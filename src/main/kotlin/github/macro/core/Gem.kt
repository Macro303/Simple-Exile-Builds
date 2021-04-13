package github.macro.core

import com.charleskorn.kaml.Yaml
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import kotlinx.serialization.decodeFromString
import org.apache.logging.log4j.LogManager
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2021-Apr-12.
 */
class Gem(
    id: String,
    name: String,
    colour: Colour,
    isVaal: Boolean,
    isSupport: Boolean,
    isAwakened: Boolean
) {
    constructor(
        id: String,
        name: String,
        colour: String,
        isVaal: Boolean,
        isSupport: Boolean,
        isAwakened: Boolean
    ) : this(
        id = id,
        name = name,
        colour = Colour.fromName(colour),
        isVaal = isVaal,
        isSupport = isSupport,
        isAwakened = isAwakened
    )

    val idProperty = SimpleStringProperty()
    var id: String by idProperty

    val nameProperty = SimpleStringProperty()
    var name: String by nameProperty

    val colourProperty = SimpleObjectProperty<Colour>()
    var colour: Colour by colourProperty

    val isVaalProperty = SimpleBooleanProperty()
    var isVaal: Boolean by isVaalProperty

    val isSupportProperty = SimpleBooleanProperty()
    var isSupport: Boolean by isSupportProperty

    val isAwakenedProperty = SimpleBooleanProperty()
    var isAwakened: Boolean by isAwakenedProperty

    init {
        this.id = id
        this.name = name
        this.colour = colour
        this.isVaal = isVaal
        this.isSupport = isSupport
        this.isAwakened = isAwakened
    }

    fun getImageFile(): File {
        var image = File(File("resources", "Images"), "$id.png")
        if (!image.exists())
            image = File(File("resources", "Images"), "placeholder.png")
        return image
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Gem) return false

        if (idProperty != other.idProperty) return false
        if (nameProperty != other.nameProperty) return false
        if (colourProperty != other.colourProperty) return false
        if (isVaalProperty != other.isVaalProperty) return false
        if (isSupportProperty != other.isSupportProperty) return false
        if (isAwakenedProperty != other.isAwakenedProperty) return false

        return true
    }

    override fun hashCode(): Int {
        var result = idProperty.hashCode()
        result = 31 * result + nameProperty.hashCode()
        result = 31 * result + colourProperty.hashCode()
        result = 31 * result + isVaalProperty.hashCode()
        result = 31 * result + isSupportProperty.hashCode()
        result = 31 * result + isAwakenedProperty.hashCode()
        return result
    }

    override fun toString(): String {
        return "Gem(idProperty=$idProperty, nameProperty=$nameProperty, colourProperty=$colourProperty, isVaalProperty=$isVaalProperty, isSupportProperty=$isSupportProperty, isAwakenedProperty=$isAwakenedProperty)"
    }

    companion object {
        private val LOGGER = LogManager.getLogger()

        private val GEM_LIST: List<Gem> by lazy {
            Files.newBufferedReader(Paths.get("resources", "Gems.json")).use {
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