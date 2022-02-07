package eu.sentinalcoding.heliosreset.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Description
import dev.triumphteam.cmd.core.annotation.SubCommand
import eu.sentinalcoding.heliosreset.HeliosReset
import eu.sentinalcoding.heliosreset.PREFIX
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player
import java.util.*

@Command(value = "heliosreset", alias = ["hr", "worldreset", "reset"])
class HeliosResetCommand(private var heliosReset: HeliosReset) : BaseCommand() {

    private val data = ArrayList<UUID>()

    @SubCommand("setworld")
    @Permission("heliosreset.setworld")
    @Description("The Command used to set the World which should be reset when the Command is executed")
    fun handleWorld(player: Player, world: String){
        if(heliosReset.configStorage.isWorldSet()){
            if(!data.contains(player.uniqueId)){
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PREFIX + "§6The World is already set, re-run the Command to confirm the overwrite!"))
                data.add(player.uniqueId)
            }else{
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PREFIX + "§cWorld Overwritten!"))
                heliosReset.configStorage.setWorld(world)
                data.remove(player.uniqueId)
            }
        }else{
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PREFIX + "§cWorld set!"))
            heliosReset.configStorage.setWorld(world)
        }
    }
    @SubCommand("reset")
    @Permission("heliosreset.reset")
    @Description("The Command to execute in order to reset the World")
    fun handleReset(player: Player){
        heliosReset.worldManager.reset(player)
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PREFIX + "§cReset in progress, §4stand by!"))
    }

    init {
        heliosReset.commandManager.registerCommand(this)
    }
}