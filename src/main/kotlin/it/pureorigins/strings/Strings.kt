package it.pureorigins.strings

import it.pureorigins.framework.configuration.configFile
import it.pureorigins.framework.configuration.json
import it.pureorigins.framework.configuration.readFileAs
import net.fabricmc.api.ModInitializer

object Strings : ModInitializer {
    lateinit var strings: Map<String, String>
    
    override fun onInitialize() {
        strings = json.readFileAs(configFile("strings.json"), HashMap())
    }
}
