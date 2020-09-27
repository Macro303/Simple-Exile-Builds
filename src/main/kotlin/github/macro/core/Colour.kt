package github.macro.core

import github.macro.settings.Settings

/**
 * Created by Macro303 on 2020-Jan-17.
 */
enum class Colour(val hex: String) {
	RED("#C44C4C"),
	GREEN("#4CC44C"),
	BLUE("#4C4CC4"),
	WHITE(if (Settings.INSTANCE.useDarkMode) "#C4C4C4" else "#4C4C4C"),
	YELLOW(if (Settings.INSTANCE.useDarkMode) "#FFD800" else "#BF9F00"),
	ORANGE(if (Settings.INSTANCE.useDarkMode) "#FF6A00" else "#BF4C00"),
	ERROR(if (Settings.INSTANCE.useDarkMode) "#4C4C4C" else "#C4C4C4");

	companion object {
		fun value(name: String?): Colour = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		} ?: ERROR
	}
}