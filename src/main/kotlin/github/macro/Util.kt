package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.ClassTag
import github.macro.build_info.ClassTag.*
import github.macro.build_info.equipment.Equipment
import github.macro.build_info.gems.Acquisition
import github.macro.build_info.gems.Gem
import github.macro.config.Config
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
	internal const val WEAPONS_SIZE = 6
	internal const val ARMOUR_SIZE = 6
	internal const val HELMET_SIZE = 4
	internal const val GLOVES_SIZE = 4
	internal const val BOOTS_SIZE = 4
	internal val JSON_MAPPER: ObjectMapper by lazy {
		val mapper = ObjectMapper()
		mapper.enable(SerializationFeature.INDENT_OUTPUT)
		mapper.findAndRegisterModules()
		mapper.registerModule(Jdk8Module())
		mapper
	}
	internal val YAML_MAPPER: ObjectMapper by lazy {
		val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
		mapper.enable(SerializationFeature.INDENT_OUTPUT)
		mapper.findAndRegisterModules()
		mapper.registerModule(Jdk8Module())
		mapper
	}
	val GEM_LIST: List<Gem> by lazy {
		try {
			JSON_MAPPER.readValue(File("resources/Gems", "Gems.json"), object : TypeReference<List<Gem>>() {})
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gems: $ioe")
			emptyList<Gem>()
		}
	}
	val MISSING_GEM = Gem(
		name = "Missing",
		colour = github.macro.build_info.gems.Colour.ERROR,
		tags = emptyList(),
		isVaal = false,
		isSupport = false,
		isAwakened = false,
		acquisition = Acquisition(emptyList(), emptyList())
	)
	val EQUIPMENT_LIST: List<Equipment> by lazy {
		try {
			JSON_MAPPER.readValue(
				File("resources/Equipment", "Equipment.json"),
				object : TypeReference<List<Equipment>>() {})
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Equipment: $ioe")
			emptyList<Equipment>()
		}
	}
	val MISSING_EQUIPMENT = Equipment(
		name = "Missing",
		slot = github.macro.build_info.equipment.Slot.ERROR,
		level = 0,
		quality = 0.0
	)

	fun slotToColour(colour: github.macro.build_info.gems.Colour?): String = when (colour) {
		github.macro.build_info.gems.Colour.RED -> "#C44C4C"
		github.macro.build_info.gems.Colour.GREEN -> "#4CC44C"
		github.macro.build_info.gems.Colour.BLUE -> "#4C4CC4"
		github.macro.build_info.gems.Colour.WHITE -> if (Config.INSTANCE.useDarkMode) "#C4C4C4" else "#4C4C4C"
		else -> if (Config.INSTANCE.useDarkMode) "#4C4C4C" else "#C4C4C4"
	}

	fun gemByName(name: String?): Gem {
		name ?: return MISSING_GEM
		return GEM_LIST.firstOrNull {
			it.getFullname().equals(name, ignoreCase = true)
		} ?: MISSING_GEM
	}

	fun equipmentByName(name: String?): Equipment {
		name ?: return MISSING_EQUIPMENT
		return EQUIPMENT_LIST.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		} ?: MISSING_EQUIPMENT
	}

	internal fun getClassGems(classTag: ClassTag): List<Gem> = when (classTag) {
		SCION -> listOf("Spectral Throw", "Onslaught Support")
		MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
		RANGER -> listOf("Burning Arrow", "Pierce Support")
		WITCH -> listOf("Fireball", "Arcane Surge Support")
		DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
		TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
		SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
	}.plus(arrayOf("Empower Support", "Vaal Haste", "Awakened Cast on Critical Strike Support")).map { gemByName(it) }

	fun Enum<*>.cleanName(): String = this.name.split("_").joinToString(" ") { it.toLowerCase().capitalize() }

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
}