package eu.sentinalcoding.heliosreset

import org.bukkit.plugin.java.JavaPlugin

class HeliosReset : JavaPlugin() {

    private val heliosLoader: HeliosLoader = HeliosLoader(this)

    override fun onEnable() {
        heliosLoader.initLoader()
    }

    fun getLoader() : HeliosLoader{
        return heliosLoader
    }




}