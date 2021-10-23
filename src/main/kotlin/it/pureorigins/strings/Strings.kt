package it.pureorigins.strings

import it.pureorigins.framework.configuration.configFile
import it.pureorigins.framework.configuration.json
import it.pureorigins.framework.configuration.readFileAs
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.command.ServerCommandSource

object Strings : ModInitializer {
    lateinit var strings: Map<String, String>
    
    internal var commandSource: ServerCommandSource? = null @JvmName("getCommandSource") get
    
    override fun onInitialize() {
        strings = json.readFileAs(configFile("strings.json"), HashMap())
        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            commandSource = server.commandSource
        }
    }
}
