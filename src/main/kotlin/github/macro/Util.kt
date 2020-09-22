package github.macro

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.core.build_info.ClassTag
import github.macro.core.build_info.ClassTag.*
import github.macro.core.items.flasks.ItemFlask
import github.macro.core.gems.Colour
import github.macro.core.gems.ItemGem
import github.macro.config.Config
import github.macro.core.Data
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.scene.control.Tooltip
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Field

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Util {
	private val LOGGER = LogManager.getLogger(Util::class.java)
	internal const val UI_PREF_WIDTH = 800.0
	internal const val UI_PREF_HEIGHT = 750.0
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

	fun colourToHex(colour: Colour?): String = when (colour) {
		Colour.RED -> "#C44C4C"
		Colour.GREEN -> "#4CC44C"
		Colour.BLUE -> "#4C4CC4"
		Colour.WHITE -> if (Config.INSTANCE.useDarkMode) "#C4C4C4" else "#4C4C4C"
		else -> if (Config.INSTANCE.useDarkMode) "#4C4C4C" else "#C4C4C4"
	}

	internal fun getStartingGems(classTag: ClassTag): List<ItemGem> = when (classTag) {
		SCION -> listOf("Spectral Throw", "Onslaught Support")
		MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
		RANGER -> listOf("Burning Arrow", "Pierce Support")
		WITCH -> listOf("Fireball", "Arcane Surge Support")
		DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
		TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
		SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
	}.map { Data.getGem(it) }

	internal fun getStartingFlasks(): List<ItemFlask> =
		listOf("Small Life Flask", "Small Life Flask", "Small Mana Flask").map { Data.getFlask(it) }

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