package github.buried_in_code.core.pantheon

/**
 * Created by Buried-In-Code on 2020-Sep-28
 */
enum class MajorPantheon {
    LUNARIS,
    ARAKAALI,
    SOLARIS,
    THE_BRINE_KING;

    companion object {
        fun fromName(name: String?): MajorPantheon? = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        }
    }
}