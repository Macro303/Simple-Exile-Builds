package github.macro

import github.macro.core.ClassTag
import github.macro.core.ClassTag.*
import github.macro.core.Colour
import github.macro.core.Gem
import org.apache.logging.log4j.LogManager
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Utils {
    private val LOGGER = LogManager.getLogger(Utils::class.java)
    internal const val UI_PREF_WIDTH = 1000.0
    internal const val UI_PREF_HEIGHT = 900.0
    internal const val IMG_SIZE = 59.0
    /*internal val JSON_MAPPER: ObjectMapper by lazy {
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.findAndRegisterModules()
        mapper.registerModule(Jdk8Module())
        mapper
    }
    internal val YAML_MAPPER: ObjectMapper by lazy {
        val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.findAndRegisterModules()
        mapper.registerModule(Jdk8Module())
        mapper
    }*/

    internal fun getStartingGems(classTag: ClassTag): List<Gem> = when (classTag) {
        SCION -> listOf("Spectral Throw", "Onslaught Support")
        MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
        RANGER -> listOf("Burning Arrow", "Pierce Support")
        WITCH -> listOf("Fireball", "Arcane Surge Support")
        DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
        TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
        SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
    }.mapNotNull { Gem.fromName(it) }

    fun Enum<*>.cleanName(): String = this.name.split("_").joinToString(" ") { it.toLowerCase().capitalize() }

    fun getImageWidth(item: Gem): Double = ImageIO.read(item.getImageFile()).width.toDouble()
    fun getImageHeight(item: Gem): Double = ImageIO.read(item.getImageFile()).height.toDouble()

    fun slugify(value: String): String{
        val regex = "[\\/:*?\"<>|.]+".toRegex()
        val temp = regex.replace(value, "")
        return temp.replace("-", " ").split(" ").joinToString("-") { it.trim().capitalize() }
    }

    fun getMissingGem(name: String? = null) = Gem(
        id = "Gem/Missing",
        name = name ?: "Missing",
        colour = Colour.ERROR,
        isVaal = false,
        isSupport = false,
        isAwakened = false
    )
}