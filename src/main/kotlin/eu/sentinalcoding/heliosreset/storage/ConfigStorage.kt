package eu.sentinalcoding.heliosreset.storage

import eu.sentinalcoding.heliosreset.HeliosReset
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.NodePath
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.properties.Delegates

class ConfigStorage(private val heliosReset: HeliosReset) {

    private var hoconConfigurationLoader : HoconConfigurationLoader by Delegates.notNull()
    private var root: CommentedConfigurationNode by Delegates.notNull()

    fun initConfig(){
        val location: Path = File(heliosReset.dataFolder.absolutePath + File.separator + "config.yml").toPath()
        if(!location.toFile().parentFile.exists()){
            Files.createDirectories(location.toFile().parentFile.toPath())
        }
        if(!location.toFile().exists()){
            Files.createFile(location)
        }

        hoconConfigurationLoader = HoconConfigurationLoader.builder().path(location).build()

        root = hoconConfigurationLoader.load()
    }

    fun getWorld() : String {
        return root.node(NodePath.path("Settings", "world")).getString("-1")
    }

    fun setWorld(world: String){
        root.node(NodePath.path("Settings", "world")).commentIfAbsent("The World to save").set(String.javaClass, world)
        save()
    }

    fun isWorldSet() : Boolean{
        return !root.node(NodePath.path("Settings", "world")).virtual()
    }

    fun save(){
        hoconConfigurationLoader.save(root)
    }
}