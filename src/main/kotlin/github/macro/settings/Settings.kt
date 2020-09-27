package github.macro.settings

import org.apache.logging.log4j.LogManager
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2020-Jul-17
 */
class Settings {
	var useDarkMode: Boolean = false

	fun save(): Settings {
		Files.newBufferedWriter(Paths.get(filename)).use {
			it.write(YAML.dumpAsMap(this))
		}
		return this
	}

	companion object {
		private const val filename = "settings.yaml"
		private val LOGGER = LogManager.getLogger(Settings::class.java)
		private val YAML: Yaml by lazy {
			val options = DumperOptions()
			options.defaultFlowStyle = DumperOptions.FlowStyle.FLOW
			options.isPrettyFlow = true
			Yaml(options)
		}

		val INSTANCE: Settings by lazy {
			load()
		}

		fun load(): Settings {
			val temp = File(filename)
			if (!temp.exists())
				Settings().save()
			return Files.newBufferedReader(Paths.get(filename)).use {
				YAML.loadAs(it, Settings::class.java)
			}.save()
		}
	}
}