package github.macro.core.item.acquisition

/**
 * Created by Macro303 on 2020-Feb-27.
 */
enum class MethodType {
	QUEST,
	VENDOR;

	companion object {
		fun value(name: String): MethodType? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}