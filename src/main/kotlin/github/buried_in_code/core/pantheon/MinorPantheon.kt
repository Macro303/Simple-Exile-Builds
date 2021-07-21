package github.buried_in_code.core.pantheon

/**
 * Created by Buried-In-Code on 2020-Sep-28
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
        fun fromName(name: String?): MinorPantheon? = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        }
    }
}