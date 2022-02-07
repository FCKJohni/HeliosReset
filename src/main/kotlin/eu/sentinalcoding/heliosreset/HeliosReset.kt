package eu.sentinalcoding.heliosreset

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import eu.sentinalcoding.heliosreset.storage.ConfigStorage
import eu.sentinalcoding.heliosreset.utils.WorldManager
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class HeliosReset : JavaPlugin() {

    var configStorage = ConfigStorage(this)
    var worldManager = WorldManager(this)
    var commandManager: BukkitCommandManager<CommandSender> = BukkitCommandManager.create(this)

}