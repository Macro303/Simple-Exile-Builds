package github.macro.core.build_info

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class ClassTag {
	SCION,
	MARAUDER,
	RANGER,
	WITCH,
	DUELIST,
	TEMPLAR,
	SHADOW;

	companion object {
		fun value(name: String?): ClassTag? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}