package github.macro.core

import com.fasterxml.jackson.core.type.TypeReference
import github.macro.Util
import github.macro.core.gems.Acquisition
import github.macro.core.gems.Colour
import github.macro.core.gems.Gem
import github.macro.core.items.amulets.Amulet
import github.macro.core.items.body_armours.BodyArmour
import github.macro.core.items.belts.Belt
import github.macro.core.items.boots.Boots
import github.macro.core.items.flasks.Flask
import github.macro.core.items.gloves.Gloves
import github.macro.core.items.helmets.Helmet
import github.macro.core.items.rings.Ring
import github.macro.core.items.weapons.Weapon
import github.macro.core.items.weapons.WeaponType
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
object Data {
	private val LOGGER = LogManager.getLogger()
	private val DATA_DIR = File("resources", "Data")

	// Gems
	val GEM_LIST: List<Gem> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Gems.json"),
				object : TypeReference<List<Gem>>() {}
			).plus(
				Gem(
					id = "None",
					name = "None",
					isReleased = false,
					colour = Colour.ERROR,
					tags = emptyList(),
					isVaal = false,
					isSupport = false,
					isAwakened = false,
					acquisition = Acquisition(emptyList(), emptyList())
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gems: $ioe")
			mutableListOf<Gem>()
		}
	}

	fun getGemById(id: String?): Gem {
		return GEM_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getGemByName(name = id)
	}

	fun getGemByName(name: String?): Gem {
		return GEM_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Gem(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			colour = Colour.ERROR,
			tags = mutableListOf(),
			isVaal = false,
			isSupport = false,
			isAwakened = false,
			acquisition = Acquisition(mutableListOf(), mutableListOf())
		)
	}

	// Weapons
	val WEAPON_LIST: List<Weapon> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Weapons.json"),
				object : TypeReference<List<Weapon>>() {}
			).plus(
				Weapon(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false,
					type = WeaponType.ONE_HAND_SWORD
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Weapons: $ioe")
			mutableListOf<Weapon>()
		}
	}

	fun getWeaponById(id: String?): Weapon {
		return WEAPON_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getWeaponByName(name = id)
	}

	fun getWeaponByName(name: String?): Weapon {
		return WEAPON_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Weapon(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false,
			type = WeaponType.ONE_HAND_SWORD
		)
	}

	// Armours
	val BODY_ARMOUR_LIST: List<BodyArmour> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Body_Armours.json"),
				object : TypeReference<List<BodyArmour>>() {}
			).plus(
				BodyArmour(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Armour: $ioe")
			mutableListOf<BodyArmour>()
		}
	}

	fun getBodyArmourById(id: String?): BodyArmour {
		return BODY_ARMOUR_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBodyArmourByName(name = id)
	}

	fun getBodyArmourByName(name: String?): BodyArmour {
		return BODY_ARMOUR_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: BodyArmour(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Helmets
	val HELMET_LIST: List<Helmet> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Helmets.json"),
				object : TypeReference<List<Helmet>>() {}
			).plus(
				Helmet(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Helmet: $ioe")
			mutableListOf<Helmet>()
		}
	}

	fun getHelmetById(id: String?): Helmet {
		return HELMET_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getHelmetByName(name = id)
	}

	fun getHelmetByName(name: String?): Helmet {
		return HELMET_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Helmet(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Gloves
	val GLOVES_LIST: List<Gloves> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Gloves.json"),
				object : TypeReference<List<Gloves>>() {}
			).plus(
				Gloves(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gloves: $ioe")
			mutableListOf<Gloves>()
		}
	}

	fun getGlovesById(id: String?): Gloves {
		return GLOVES_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getGlovesByName(name = id)
	}

	fun getGlovesByName(name: String?): Gloves {
		return GLOVES_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Gloves(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Boots
	val BOOTS_LIST: List<Boots> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Boots.json"),
				object : TypeReference<List<Boots>>() {}
			).plus(
				Boots(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Boots: $ioe")
			mutableListOf<Boots>()
		}
	}

	fun getBootsById(id: String?): Boots {
		return BOOTS_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBootsByName(name = id)
	}

	fun getBootsByName(name: String?): Boots {
		return BOOTS_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Boots(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Belt
	val BELT_LIST: List<Belt> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Belts.json"),
				object : TypeReference<List<Belt>>() {}
			).plus(
				Belt(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Belt: $ioe")
			mutableListOf<Belt>()
		}
	}

	fun getBeltById(id: String?): Belt {
		return BELT_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBeltByName(name = id)
	}

	fun getBeltByName(name: String?): Belt {
		return BELT_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Belt(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Amulet
	val AMULET_LIST: List<Amulet> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Amulets.json"),
				object : TypeReference<List<Amulet>>() {}
			).plus(
				Amulet(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Amulet: $ioe")
			mutableListOf<Amulet>()
		}
	}

	fun getAmuletById(id: String?): Amulet {
		return AMULET_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getAmuletByName(name = id)
	}

	fun getAmuletByName(name: String?): Amulet {
		return AMULET_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Amulet(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Rings
	val RING_LIST: List<Ring> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Rings.json"),
				object : TypeReference<List<Ring>>() {}
			).plus(
				Ring(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Rings: $ioe")
			mutableListOf<Ring>()
		}
	}

	fun getRingById(id: String?): Ring {
		return RING_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getRingByName(name = id)
	}

	fun getRingByName(name: String?): Ring {
		return RING_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Ring(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Flasks
	val FLASK_LIST: List<Flask> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Flasks.json"),
				object : TypeReference<List<Flask>>() {}
			).plus(
				Flask(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Flasks: $ioe")
			mutableListOf<Flask>()
		}
	}

	fun getFlaskById(id: String?): Flask {
		return FLASK_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getFlaskByName(name = id)
	}

	fun getFlaskByName(name: String?): Flask {
		return FLASK_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: Flask(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}
}