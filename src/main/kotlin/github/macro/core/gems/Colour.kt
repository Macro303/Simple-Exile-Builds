package github.macro.core.gems

/**
 * Created by Macro303 on 2020-Jan-17.
 */
enum class Colour {
	GREEN,
	BLUE,
	RED,
	WHITE,
	ERROR;

	companion object {
		fun value(name: String): Colour? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}