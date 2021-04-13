package github.macro.settings

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2020-Jul-17
 */
@Serializable
class Settings {
    var useDarkMode: Boolean = false

    fun save(): Settings {
        Files.newBufferedWriter(Paths.get(filename)).use {
            it.write(Yaml.default.encodeToString(this))
        }
        return this
    }

    companion object {
        private const val filename = "settings.yaml"
        private val LOGGER = LogManager.getLogger(Settings::class.java)
        /*private val YAML: Yaml by lazy {
            val options = DumperOptions()
            options.defaultFlowStyle = DumperOptions.FlowStyle.FLOW
            options.isPrettyFlow = true
            Yaml(options)
        }*/

        val INSTANCE: Settings by lazy {
            load()
        }

        fun load(): Settings {
            val temp = File(filename)
            if (!temp.exists())
                Settings().save()
            return Files.newBufferedReader(Paths.get(filename)).use {
                Yaml.default.decodeFromString<Settings>(it.readText())
            }.save()
        }
    }
}