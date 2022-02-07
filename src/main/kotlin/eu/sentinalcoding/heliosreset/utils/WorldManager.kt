package eu.sentinalcoding.heliosreset.utils

import com.okkero.skedule.schedule
import eu.sentinalcoding.heliosreset.Constants
import eu.sentinalcoding.heliosreset.HeliosReset
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import kotlin.properties.Delegates

class WorldManager(private val heliosReset: HeliosReset) {

    private var world: World by Delegates.notNull()
    private var directory: Path by Delegates.notNull()
    private var scheduler = Bukkit.getScheduler()

    fun initManager(){
        val worldName = heliosReset.getLoader().getConfig().getWorld()
        if(worldName == "-1"){
            Bukkit.getConsoleSender().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("Invalid or none world saved, use /hr setworld [World] to set a valid world and restart the Server."))
            return
        }
        world = Bukkit.createWorld(WorldCreator(heliosReset.getLoader().getConfig().getWorld()))!!
        directory = heliosReset.dataFolder.toPath().resolve("saves")
        if(!directory.toFile().exists()){
            Files.createDirectories(directory)
        }

        if(!Files.exists(directory.resolve(world.name))){
            Bukkit.getConsoleSender().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§cUnloading world to Save!"))
            if(Bukkit.unloadWorld(world, true)) {
                scheduler.schedule(heliosReset) {
                    waitFor(60)
                    copyWorld(world.worldFolder.toPath(), directory.resolve(world.name))
                    Bukkit.getConsoleSender().sendMessage(
                        LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(Constants.PREFIX + "§cSaving of World §8[§6" + world.name + "§8] §ccompleted!")
                    )
                    WorldCreator(world.name)
                }
            }
}

    }

    fun reset(invoker: Player){
        for (player in world.players) {
            player.teleport(Bukkit.getWorlds()[0].spawnLocation)
        }
        if(Bukkit.unloadWorld(world, false)) {
            scheduler.schedule(heliosReset) {
                waitFor(60)
                Files.walk(world.worldFolder.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete)
                if (!Files.exists(directory.resolve(world.name))) {
                    Bukkit.getConsoleSender().sendMessage(
                        LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(Constants.PREFIX + "§cNo save of this world found!")
                    )
                }
                copyWorld(directory.resolve(world.name), world.worldFolder.toPath())
                WorldCreator(world.name)
                invoker.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§cReset complete!"))
            }
        }

    }

    private fun copyWorld(from: Path, to: Path){
        if(Files.isDirectory(from)){
            if(!Files.exists(to))
                Files.createDirectories(to)
            val files = from.toFile().list()
            for (file in files) {
                val src = File(from.toFile(), file)
                val dest = File(to.toFile(), file)
                copyWorld(src.toPath(), dest.toPath())
            }
        }else{
            if(from.toFile().name == "session.lock") return
            val inputStream: InputStream = FileInputStream(from.toFile())
            val outputStream: OutputStream = FileOutputStream(to.toFile())
            val buffer = ByteArray(8192)
            inputStream.use { input ->
                outputStream.use { out ->

                    while (true) {
                        val length = input.read(buffer)
                        if (length <= 0)
                            break
                        out.write(buffer, 0, length)
                    }
                    out.flush()
                    out.close()
                }
            }
        }
    }
}