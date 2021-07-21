package github.buried_in_code.ui.config

import com.charleskorn.kaml.Yaml
import github.buried_in_code.Config
import javafx.beans.property.SimpleBooleanProperty
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.apache.logging.log4j.LogManager
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Buried-In-Code on 2021-Apr-14.
 */
class ConfigModel : ViewModel() {
    val darkModeProperty = SimpleBooleanProperty()
    var darkMode by darkModeProperty

    init {
        load()
    }


    fun save() {
        Files.newBufferedWriter(Paths.get(filename)).use {
            it.write(Yaml.default.encodeToString(Config(darkMode)))
        }
    }

    fun load() {
        File(filename).apply {
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

    companion object {
        private const val filename = "config.yaml"
        private val LOGGER = LogManager.getLogger()
    }
}