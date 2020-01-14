package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.ClassTag
import github.macro.build_info.ClassTag.*
import github.macro.build_info.gems.BuildGem
import github.macro.build_info.gems.GemInfo
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.scene.control.Tooltip
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException
import java.lang.reflect.Field

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

	internal fun hackTooltipStartTiming(tooltip: Tooltip) {
		try {
			val fieldBehavior: Field = tooltip.javaClass.getDeclaredField("BEHAVIOR")
			fieldBehavior.isAccessible = true
			val objBehavior: Any = fieldBehavior.get(tooltip)
			val fieldTimer: Field = objBehavior.javaClass.getDeclaredField("activationTimer")
			fieldTimer.isAccessible = true
			val objTimer = fieldTimer.get(objBehavior) as Timeline
			objTimer.keyFrames.clear()
			objTimer.keyFrames.add(KeyFrame(Duration(250.0)))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	internal fun getClassGems(classTag: ClassTag): List<BuildGem> = when (classTag) {
		SCION -> listOf(textToGem("Spectral Throw"), textToGem("Onslaught Support"))
		MARAUDER -> listOf(textToGem("Heavy Strike"), textToGem("Ruthless Support"))
		RANGER -> listOf(textToGem("Burning Arrow"), textToGem("Pierce Support"))
		WITCH -> listOf(textToGem("Fireball"), textToGem("Arcane Surge Support"))
		DUELIST -> listOf(textToGem("Double Strike"), textToGem("Chance to Bleed Support"))
		TEMPLAR -> listOf(textToGem("Glacial Hammer"), textToGem("Elemental Proliferation Support"))
		SHADOW -> listOf(textToGem("Viper Strike"), textToGem("Lesser Poison Support"))
	}.plus(textToGem("Empower Support"))
}