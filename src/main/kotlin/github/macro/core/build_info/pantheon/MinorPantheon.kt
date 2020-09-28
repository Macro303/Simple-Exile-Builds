package github.macro.core.build_info.pantheon

/**
 * Created by Macro303 on 2020-Sep-28
 */
enum class MinorPantheon {
	YUGUL,
	RALAKESH,
	SHAKARI,
	ABBERATH,
	TUKOHAMA,
	RYSLATHA,
	GARUKHAN,
	GRUTHKUL;

	companion object {
		fun value(name: String?): MinorPantheon? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}