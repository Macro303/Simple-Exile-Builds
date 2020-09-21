package github.macro.build_info.gear

/**
 * Created by Macro303 on 2020-Jan-16.
 */
enum class Slot {
	WEAPON,
	CHEST,
	BOOTS,
	GLOVES,
	HELMET,
	AMULET,
	BELT,
	RING,
	FLASK,
	ERROR;

	companion object {
		fun value(name: String): Slot? = values().firstOrNull { it.name.equals(name, ignoreCase = true) }
	}
}