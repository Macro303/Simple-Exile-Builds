package github.macro.build_info.gems

/**
 * Created by Macro303 on 2020-Jan-17.
 */
enum class Slot {
    GREEN,
    BLUE,
    RED,
    WHITE;

    companion object {
        fun value(name: String): Slot? = values().firstOrNull { it.name.equals(name, ignoreCase = true) }
    }
}