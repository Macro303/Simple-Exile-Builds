package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.gems.BuildGem
import github.macro.build_info.gems.GemInfo
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Util {
	private val LOGGER = LogManager.getLogger(Util::class.java)
	internal val JSON_MAPPER = ObjectMapper()
	internal val YAML_MAPPER = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
	val gems by lazy {
		try {
			JSON_MAPPER.readValue(File("gems", "Gems.json"), object : TypeReference<List<GemInfo>>() {})
		} catch (ioe: IOException) {
			LOGGER.error("Unable to Load Gems: $ioe")
			emptyList<GemInfo>()
		}
	}

	init {
		JSON_MAPPER.enable(SerializationFeature.INDENT_OUTPUT)
		JSON_MAPPER.findAndRegisterModules()
		JSON_MAPPER.registerModule(Jdk8Module())
		YAML_MAPPER.enable(SerializationFeature.INDENT_OUTPUT)
		YAML_MAPPER.findAndRegisterModules()
		YAML_MAPPER.registerModule(Jdk8Module())
	}

	fun slotToColour(slot: String?): String {
		return when (slot) {
			"Red" -> "#DD9999"
			"Green" -> "#99DD99"
			"Blue" -> "#9999DD"
			"White" -> "#DDDDDD"
			else -> "#999999"
		}
	}

	fun textToGem(name: String): BuildGem {
		val info = gems.firstOrNull {
			when {
				it.hasVaal && "Vaal ${it.name}".equals(name, ignoreCase = true) -> return@firstOrNull true
				it.hasAwakened && "Awakened ${it.name}".equals(name, ignoreCase = true) -> return@firstOrNull true
				it.name.equals(name, ignoreCase = true) -> return@firstOrNull true
				else -> return@firstOrNull false
			}
		}
		return BuildGem(info, name.startsWith("Vaal"), name.startsWith("Awakened"))
	}
}