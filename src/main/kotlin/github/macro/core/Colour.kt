package github.macro.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Macro303 on 2020-Jan-17.
 */
@Serializable
enum class Colour(val hex: String) {
    @SerialName("Red")
    RED("#C44C4C"),

    @SerialName("Green")
    GREEN("#4CC44C"),

    @SerialName("Blue")
    BLUE("#4C4CC4"),

    @SerialName("White")
    WHITE("#C4C4C4"),

    @SerialName("Yellow")
    YELLOW("#FFD800"),

    @SerialName("Orange")
    ORANGE("#FF6A00"),

    @SerialName("Error")
    ERROR("#4C4C4C");

    companion object {
        fun fromName(name: String?): Colour = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        } ?: ERROR
    }
}