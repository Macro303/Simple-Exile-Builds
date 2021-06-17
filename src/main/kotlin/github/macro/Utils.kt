package github.macro

import github.macro.core.ClassTag
import github.macro.core.ClassTag.*
import github.macro.core.Colour
import github.macro.core.Gem
import org.apache.logging.log4j.LogManager
import java.io.InputStream
import javax.imageio.ImageIO

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Utils {
    private val LOGGER = LogManager.getLogger(Utils::class.java)
    internal const val UI_PREF_WIDTH = 1000.0
    internal const val UI_PREF_HEIGHT = 900.0
    internal const val IMG_SIZE = 59.0

    internal fun getStartingGems(classTag: ClassTag): List<Gem> = when (classTag) {
        SCION -> listOf("Spectral Throw", "Onslaught Support")
        MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
        RANGER -> listOf("Burning Arrow", "Pierce Support")
        WITCH -> listOf("Fireball", "Arcane Surge Support")
        DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
        TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
        SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
    }.mapNotNull { Gem.fromName(it) }

    fun Enum<*>.cleanName(): String = this.name.split("_").joinToString(" ") {
        it.lowercase().replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() }
    }

    fun getImageWidth(item: Gem): Double = ImageIO.read(item.getImageFile()).width.toDouble()
    fun getImageHeight(item: Gem): Double = ImageIO.read(item.getImageFile()).height.toDouble()

    fun slugify(value: String): String {
        val regex = "[\\/:*?\"<>|.]+".toRegex()
        val temp = regex.replace(value, "")
        return temp.replace("-", " ").split(" ").joinToString("-") {
            it.trim().replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() }
        }
    }

    fun getMissingGem(name: String? = null) = Gem(
        id = "Gem/Missing",
        name = name ?: "Missing",
        colour = Colour.ERROR,
        isVaal = false,
        isSupport = false,
        isAwakened = false
    )

    fun getResource(location: String): String = Launcher::class.java.getResource(location)!!.toExternalForm()
    fun getResourceStream(location: String): InputStream = Launcher::class.java.getResource(location)!!.openStream()
}