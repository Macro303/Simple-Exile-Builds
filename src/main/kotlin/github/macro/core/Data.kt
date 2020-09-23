package github.macro.core

import com.fasterxml.jackson.core.type.TypeReference
import github.macro.Util
import github.macro.core.gems.Acquisition
import github.macro.core.gems.Colour
import github.macro.core.gems.ItemGem
import github.macro.core.items.amulets.ItemAmulet
import github.macro.core.items.body_armours.ItemBodyArmour
import github.macro.core.items.belts.ItemBelt
import github.macro.core.items.boots.ItemBoots
import github.macro.core.items.flasks.ItemFlask
import github.macro.core.items.gloves.ItemGloves
import github.macro.core.items.helmets.ItemHelmet
import github.macro.core.items.rings.ItemRing
import github.macro.core.items.weapons.ItemWeapon
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
	val GEM_LIST: List<ItemGem> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Gems.json"),
				object : TypeReference<List<ItemGem>>() {}
			).plus(
				ItemGem(
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
			mutableListOf<ItemGem>()
		}
	}

	fun getGemById(id: String?): ItemGem {
		return GEM_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getGemByName(name = id)
	}

	fun getGemByName(name: String?): ItemGem {
		return GEM_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemGem(
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
	val WEAPON_LIST: List<ItemWeapon> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Weapons.json"),
				object : TypeReference<List<ItemWeapon>>() {}
			).plus(
				ItemWeapon(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false,
					type = WeaponType.ONE_HAND_SWORD
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Weapons: $ioe")
			mutableListOf<ItemWeapon>()
		}
	}

	fun getWeaponById(id: String?): ItemWeapon {
		return WEAPON_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getWeaponByName(name = id)
	}

	fun getWeaponByName(name: String?): ItemWeapon {
		return WEAPON_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemWeapon(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false,
			type = WeaponType.ONE_HAND_SWORD
		)
	}

	// Armours
	val BODY_ARMOUR_LIST: List<ItemBodyArmour> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Body_Armours.json"),
				object : TypeReference<List<ItemBodyArmour>>() {}
			).plus(
				ItemBodyArmour(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Armour: $ioe")
			mutableListOf<ItemBodyArmour>()
		}
	}

	fun getBodyArmourById(id: String?): ItemBodyArmour {
		return BODY_ARMOUR_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBodyArmourByName(name = id)
	}

	fun getBodyArmourByName(name: String?): ItemBodyArmour {
		return BODY_ARMOUR_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemBodyArmour(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Helmets
	val HELMET_LIST: List<ItemHelmet> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Helmets.json"),
				object : TypeReference<List<ItemHelmet>>() {}
			).plus(
				ItemHelmet(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Helmet: $ioe")
			mutableListOf<ItemHelmet>()
		}
	}

	fun getHelmetById(id: String?): ItemHelmet {
		return HELMET_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getHelmetByName(name = id)
	}

	fun getHelmetByName(name: String?): ItemHelmet {
		return HELMET_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemHelmet(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Gloves
	val GLOVES_LIST: List<ItemGloves> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Gloves.json"),
				object : TypeReference<List<ItemGloves>>() {}
			).plus(
				ItemGloves(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Gloves: $ioe")
			mutableListOf<ItemGloves>()
		}
	}

	fun getGlovesById(id: String?): ItemGloves {
		return GLOVES_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getGlovesByName(name = id)
	}

	fun getGlovesByName(name: String?): ItemGloves {
		return GLOVES_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemGloves(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Boots
	val BOOTS_LIST: List<ItemBoots> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Boots.json"),
				object : TypeReference<List<ItemBoots>>() {}
			).plus(
				ItemBoots(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Boots: $ioe")
			mutableListOf<ItemBoots>()
		}
	}

	fun getBootsById(id: String?): ItemBoots {
		return BOOTS_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBootsByName(name = id)
	}

	fun getBootsByName(name: String?): ItemBoots {
		return BOOTS_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemBoots(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Belt
	val BELT_LIST: List<ItemBelt> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Belts.json"),
				object : TypeReference<List<ItemBelt>>() {}
			).plus(
				ItemBelt(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Belt: $ioe")
			mutableListOf<ItemBelt>()
		}
	}

	fun getBeltById(id: String?): ItemBelt {
		return BELT_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getBeltByName(name = id)
	}

	fun getBeltByName(name: String?): ItemBelt {
		return BELT_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemBelt(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Amulet
	val AMULET_LIST: List<ItemAmulet> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Amulets.json"),
				object : TypeReference<List<ItemAmulet>>() {}
			).plus(
				ItemAmulet(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Amulet: $ioe")
			mutableListOf<ItemAmulet>()
		}
	}

	fun getAmuletById(id: String?): ItemAmulet {
		return AMULET_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getAmuletByName(name = id)
	}

	fun getAmuletByName(name: String?): ItemAmulet {
		return AMULET_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemAmulet(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Rings
	val RING_LIST: List<ItemRing> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Rings.json"),
				object : TypeReference<List<ItemRing>>() {}
			).plus(
				ItemRing(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Rings: $ioe")
			mutableListOf<ItemRing>()
		}
	}

	fun getRingById(id: String?): ItemRing {
		return RING_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getRingByName(name = id)
	}

	fun getRingByName(name: String?): ItemRing {
		return RING_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemRing(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}

	// Flasks
	val FLASK_LIST: List<ItemFlask> by lazy {
		try {
			Util.JSON_MAPPER.readValue(
				File(DATA_DIR, "Flasks.json"),
				object : TypeReference<List<ItemFlask>>() {}
			).plus(
				ItemFlask(
					id = "None",
					name = "None",
					isReleased = false,
					isUnique = false
				)
			)
		} catch (ioe: IOException) {
			LOGGER.warn("Unable to Load Flasks: $ioe")
			mutableListOf<ItemFlask>()
		}
	}

	fun getFlaskById(id: String?): ItemFlask {
		return FLASK_LIST.firstOrNull {
			it.id.equals(id, ignoreCase = true)
		} ?: getFlaskByName(name = id)
	}

	fun getFlaskByName(name: String?): ItemFlask {
		return FLASK_LIST.firstOrNull {
			it.getDisplayName().equals(name, ignoreCase = true)
		} ?: ItemFlask(
			id = "Missing",
			name = name ?: "Missing",
			isReleased = false,
			isUnique = false
		)
	}
}