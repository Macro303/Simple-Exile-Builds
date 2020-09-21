package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import github.macro.build_info.gear.Gear
import github.macro.build_info.gear.Slot
import github.macro.build_info.flasks.Flask
import github.macro.build_info.gems.Acquisition
import github.macro.build_info.gems.Colour
import github.macro.build_info.gems.Gem
import github.macro.build_info.rings.Ring
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
object Data {
	private val LOGGER = LogManager.getLogger(Data::class.java)

	val GEM_LIST: List<Gem> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(File("resources", "Gems"), "Gems.json"),
				object : TypeReference<List<Gem>>() {}
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gems: $ioe")
			emptyList<Gem>()
		}
	}
	val MISSING_GEM = Gem(
		id = "Missing",
		name = "Missing",
		colour = Colour.ERROR,
		tags = emptyList(),
		isVaal = false,
		isSupport = false,
		isAwakened = false,
		acquisition = Acquisition(emptyList(), emptyList())
	)
	val FLASK_LIST: List<Flask> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(File("resources", "Flasks"), "Flasks.json"),
				object : TypeReference<List<Flask>>() {}
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Flasks: $ioe")
			emptyList<Flask>()
		}
	}
	val MISSING_FLASK = Flask(
		id = "Missing",
		name = "Missing",
		isUnique = false,
		isReleased = false
	)
	val RING_LIST: List<Ring> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(File("resources", "Rings"), "Rings.json"),
				object : TypeReference<List<Ring>>() {}
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Rings: $ioe")
			emptyList<Ring>()
		}
	}
	val MISSING_RING = Ring(
		id = "Missing",
		name = "Missing",
		isUnique = false,
		isReleased = false
	)
	val GEAR_LIST: List<Gear> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(File("resources", "Gear"), "Gear.json"),
				object : TypeReference<List<Gear>>() {}
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gear: $ioe")
			emptyList<Gear>()
		}
	}
	val MISSING_EQUIPMENT = Gear(
		name = "Missing",
		slot = Slot.ERROR,
		level = 0,
		quality = 0.0
	)

	fun gemByName(name: String?): Gem {
		name ?: return MISSING_GEM
		return GEM_LIST.firstOrNull {
			it.getFullname().equals(name, ignoreCase = true)
		} ?: MISSING_GEM
	}

	fun flaskByName(name: String?): Flask {
		name ?: return MISSING_FLASK
		return FLASK_LIST.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		} ?: MISSING_FLASK
	}

	fun ringByName(name: String?): Ring {
		name ?: return MISSING_RING
		return RING_LIST.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		} ?: MISSING_RING
	}

	fun gearByName(name: String?): Gear {
		name ?: return MISSING_EQUIPMENT
		return GEAR_LIST.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		} ?: MISSING_EQUIPMENT
	}
}