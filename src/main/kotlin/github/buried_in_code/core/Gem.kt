package github.buried_in_code.core

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2021-Apr-12.
 */
@Serializable
data class Gem(
    val id: String,
    val name: String,
    val colour: Colour,
    val isVaal: Boolean,
    val isSupport: Boolean,
    val isAwakened: Boolean
) {

    fun getImageFile(): File {
        var image = File("resources", "$id.png")
        if (!image.exists())
            image = File("resources", "placeholder.png")
        return image
    }

    companion object {
        private val LOGGER = LogManager.getLogger()

        internal val GEM_LIST: List<Gem> by lazy {
            Files.newBufferedReader(Paths.get("resources", "Gem.json")).use {
                Yaml.default.decodeFromString(it.readText())
            }
        }

        fun fromId(id: String?): Gem? = GEM_LIST.firstOrNull {
            it.id.equals(id, ignoreCase = true)
        }

        fun fromName(name: String?): Gem? = GEM_LIST.firstOrNull {
            it.name.equals(name, ignoreCase = true)
        }
    }
}