package eu.sentinalcoding.heliosreset

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import eu.sentinalcoding.heliosreset.commands.HeliosResetCommand
import eu.sentinalcoding.heliosreset.storage.ConfigStorage
import eu.sentinalcoding.heliosreset.utils.WorldManager
import org.bukkit.command.CommandSender
import kotlin.properties.Delegates

class HeliosLoader(private val heliosReset: HeliosReset) {

    private var configStorage: ConfigStorage by Delegates.notNull()
    private var worldManager: WorldManager by Delegates.notNull()
    private var commandManager: BukkitCommandManager<CommandSender> = BukkitCommandManager.create(heliosReset)

    fun initLoader(){
        configStorage = ConfigStorage(heliosReset)
        configStorage.initConfig()

        worldManager = WorldManager(heliosReset)
        worldManager.initManager()

        commandManager.registerCommand(HeliosResetCommand(heliosReset))
    }

    fun getConfig() : ConfigStorage{
        return configStorage
    }

    fun getManager() : WorldManager {
        return worldManager
    }

}