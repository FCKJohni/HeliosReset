package eu.sentinalcoding.heliosreset.commands

import dev.triumphteam.cmd.bukkit.annotation.Permission
import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Description
import dev.triumphteam.cmd.core.annotation.SubCommand
import eu.sentinalcoding.heliosreset.Constants
import eu.sentinalcoding.heliosreset.HeliosReset
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

@Command(value = "heliosreset", alias = ["hr", "worldreset", "reset"])
class HeliosResetCommand(private var heliosReset: HeliosReset) : BaseCommand() {

    private val data = ArrayList<UUID>()

    @SubCommand("setworld")
    @Permission("heliosreset.setworld")
    @Description("The Command used to set the World which should be reset when the Command is executed")
    fun handleWorld(player: Player, world: String){
        if(heliosReset.getLoader().getConfig().isWorldSet()){
            if(!data.contains(player.uniqueId)){
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§6The World is already set, re-run the Command to confirm the overwrite!"))
                data.add(player.uniqueId)
            }else{
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§cWorld Overwritten!"))
                heliosReset.getLoader().getConfig().setWorld(world)
                data.remove(player.uniqueId)
            }
        }else{
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§cWorld set!"))
            heliosReset.getLoader().getConfig().setWorld(world)
        }
    }
    @SubCommand("reset")
    @Permission("heliosreset.reset")
    @Description("The Command to execute in order to reset the World")
    fun handleReset(player: Player){
        heliosReset.getLoader().getManager().reset(player)
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Constants.PREFIX + "§cReset in progress, §4stand by!"))
    }
}