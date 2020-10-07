package github.macro

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.core.build_info.ClassTag
import github.macro.core.build_info.ClassTag.*
import github.macro.core.item.BaseItem
import github.macro.core.item.Items
import github.macro.core.item.gear.flask.Flask
import github.macro.core.item.gear.weapons.Weapon
import github.macro.core.item.gem.Gem
import org.apache.logging.log4j.LogManager
import javax.imageio.ImageIO

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Util {
	private val LOGGER = LogManager.getLogger(Util::class.java)
	internal const val UI_PREF_WIDTH = 1000.0
	internal const val UI_PREF_HEIGHT = 900.0
	internal const val IMG_SIZE = 59.0
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

	internal fun getStartingGems(classTag: ClassTag): List<Gem> = when (classTag) {
		SCION -> listOf("Spectral Throw", "Onslaught Support")
		MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
		RANGER -> listOf("Burning Arrow", "Pierce Support")
		WITCH -> listOf("Fireball", "Arcane Surge Support")
		DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
		TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
		SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
	}.map { Items.getGemByName(it) }

	internal fun getStartingWeapons(classTag: ClassTag): List<Weapon> = when (classTag) {
		SCION -> listOf("Rusted Sword")
		MARAUDER -> listOf("Driftwood Club")
		RANGER -> listOf("Crude Bow")
		WITCH -> listOf("Driftwood Wand")
		DUELIST -> listOf("Rusted Sword")
		TEMPLAR -> listOf("Driftwood Sceptre")
		SHADOW -> listOf("Glass Shank")
	}.map { Items.getWeaponByName(it) }

	internal fun getStartingFlasks(): List<Flask> =
		listOf("Small Life Flask", "Small Life Flask", "Small Mana Flask").map { Items.getFlaskByName(it) }

	fun Enum<*>.cleanName(): String = this.name.split("_").joinToString(" ") { it.toLowerCase().capitalize() }

	fun getImageWidth(item: BaseItem): Double = ImageIO.read(item.getImageFile()).width.toDouble()
	fun getImageHeight(item: BaseItem): Double = ImageIO.read(item.getImageFile()).height.toDouble()
}