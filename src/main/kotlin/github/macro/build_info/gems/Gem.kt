package github.macro.build_info.gems

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class Gem(
    name: String,
    slot: Slot,
    tags: List<GemTag>,
    isVaal: Boolean,
    isAwakened: Boolean,
    acquisition: Acquisition
) {
    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val slotProperty = SimpleObjectProperty<Slot>()
    var slot by slotProperty

    val tagsProperty = SimpleListProperty<GemTag>()
    var tags by tagsProperty

    val isVaalProperty = SimpleBooleanProperty()
    var isVaal by isVaalProperty

    val isAwakenedProperty = SimpleBooleanProperty()
    var isAwakened by isAwakenedProperty

    val acquisitionProperty = SimpleObjectProperty<Acquisition>()
    var acquisition by acquisitionProperty

    init {
        this.name = name
        this.slot = slot
        this.tags = FXCollections.observableList(tags)
        this.isVaal = isVaal
        this.isAwakened = isAwakened
        this.acquisition = acquisition
    }

    fun getFilename(): String {
        var output = name.replace(" ", "_")
        if (isVaal)
            output += "[Vaal]"
        if (isAwakened)
            output += "[Awakened]"
        return "$output.png"
    }

    fun getFullname(): String {
        var output = ""
        if (isVaal)
            output += "Vaal "
        if (isAwakened)
            output += "Awakened "
        return output + name
    }

    fun getDisplay(): String {
        var output = name
        if (isVaal)
            output += " [Vaal]"
        if (isAwakened)
            output += " [Awakened]"
        return output
    }

    override fun toString(): String {
        return "GemInfo(name=$name, slot=$slot, tags=$tags, isVaal=$isVaal, isAwakened=$isAwakened, acquisition=$acquisition)"
    }
}
