package github.macro.ui.config

import com.charleskorn.kaml.Yaml
import github.macro.Config
import javafx.beans.property.SimpleBooleanProperty
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2021-Apr-14.
 */
class ConfigModel : ViewModel() {
    val darkModeProperty = SimpleBooleanProperty()
    var darkMode by darkModeProperty

    init {
        load()
    }


    fun save() {
        Files.newBufferedWriter(Paths.get("Config.yaml")).use {
            it.write(Yaml.default.encodeToString(Config(darkMode)))
        }
    }

    fun load() {
        File("Config.yaml").apply {
            darkMode = if (exists()) {
                val config = Files.newBufferedReader(toPath()).use { br ->
                    Yaml.default.decodeFromString<Config>(br.readText())
                }
                config.darkMode
            } else
                true
            save()
        }
    }
}