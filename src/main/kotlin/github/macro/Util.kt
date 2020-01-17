package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.ClassTag
import github.macro.build_info.ClassTag.*
import github.macro.build_info.equipment.EquipmentInfo
import github.macro.build_info.gems.GemInfo
import github.macro.build_info.gems.Slot
import github.macro.build_info.gems.Slot.*
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
	val equipment by lazy {
		try {
			JSON_MAPPER.readValue(File("equipment", "Equipment.json"), object : TypeReference<List<EquipmentInfo>>() {})
		} catch (ioe: IOException) {
			LOGGER.error("Unable to Load Equipment: $ioe")
			emptyList<EquipmentInfo>()
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

	fun slotToColour(slot: Slot?): String {
		return when (slot) {
			RED -> "#DD9999"
			GREEN -> "#99DD99"
			BLUE -> "#9999DD"
			WHITE -> "#DDDDDD"
			else -> "#999999"
		}
	}

	fun gemByName(name: String): GemInfo? = gems.firstOrNull {
		it.getFullname().equals(name, ignoreCase = true)
	}

	fun equipmentByName(name: String): EquipmentInfo? {
		return equipment.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		}
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
			objTimer.keyFrames.add(KeyFrame(Duration(0.0)))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	internal fun getClassGems(classTag: ClassTag): List<GemInfo?> = when (classTag) {
		SCION -> listOf(gemByName("Spectral Throw"), gemByName("Onslaught Support"))
		MARAUDER -> listOf(gemByName("Heavy Strike"), gemByName("Ruthless Support"))
		RANGER -> listOf(gemByName("Burning Arrow"), gemByName("Pierce Support"))
		WITCH -> listOf(gemByName("Fireball"), gemByName("Arcane Surge Support"))
		DUELIST -> listOf(gemByName("Double Strike"), gemByName("Chance to Bleed Support"))
		TEMPLAR -> listOf(gemByName("Glacial Hammer"), gemByName("Elemental Proliferation Support"))
		SHADOW -> listOf(gemByName("Viper Strike"), gemByName("Lesser Poison Support"))
	}.plus(gemByName("Empower Support"))
}