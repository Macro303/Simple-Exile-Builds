package github.macro.core.items.weapons

import github.macro.Util.cleanName

/**
 * Created by Macro303 on 2020-Sep-23
 */
enum class WeaponType(val imageWidth: Double, val imageHeight: Double) {
	BOW(156.0, 234.0),
	CLAW(156.0, 156.0),
	DAGGER(78.0, 234.0),
	FISHING_ROD(78.0, 312.0),
	ONE_HAND_AXE(78.0, 234.0),
	ONE_HAND_MACE(78.0, 234.0),
	ONE_HAND_SWORD(78.0, 234.0),
	QUIVER(156.0, 234.0),
	SCEPTRE(78.0, 234.0),
	SHIELD(156.0, 312.0),
	STAFF(78.0, 312.0),
	TWO_HAND_AXE(156.0, 312.0),
	TWO_HAND_MACE(156.0, 312.0),
	TWO_HAND_SWORD(156.0, 312.0),
	WAND(78.0, 234.0),
	WARSTAFF(156.0, 312.0),
	RUNE_DAGGER(78.0, 234.0),
	THRUSTING_ONE_HAND_SWORD(78.0, 312.0);

	companion object {
		fun value(name: String?): WeaponType? = values().firstOrNull {
			it.cleanName().equals(name, ignoreCase = true)
		}
	}
}