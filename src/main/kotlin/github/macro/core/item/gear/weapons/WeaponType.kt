package github.macro.core.item.gear.weapons

import github.macro.Util.cleanName

/**
 * Created by Macro303 on 2020-Sep-23
 */
enum class WeaponType {
	BOW,
	CLAW,
	DAGGER,
	FISHING_ROD,
	ONE_HAND_AXE,
	ONE_HAND_MACE,
	ONE_HAND_SWORD,
	QUIVER,
	SCEPTRE,
	SHIELD,
	STAFF,
	TWO_HAND_AXE,
	TWO_HAND_MACE,
	TWO_HAND_SWORD,
	WAND,
	WARSTAFF,
	RUNE_DAGGER,
	THRUSTING_ONE_HAND_SWORD;

	companion object {
		fun value(name: String?): WeaponType? = values().firstOrNull {
			it.cleanName().equals(name, ignoreCase = true)
		}
	}
}