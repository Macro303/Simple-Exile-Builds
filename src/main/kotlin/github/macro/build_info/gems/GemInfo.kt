package github.macro.build_info.gems

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class GemInfo(
	name: String,
	slot: String,
	tags: List<GemTag>,
	hasVaal: Boolean,
	hasAwakened: Boolean,
	acquisition: Acquisition
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val slotProperty = SimpleStringProperty()
	var slot by slotProperty

	val tagsProperty = SimpleListProperty<GemTag>()
	var tags by tagsProperty

	val hasVaalProperty = SimpleBooleanProperty()
	var hasVaal by hasVaalProperty

	val hasAwakenedProperty = SimpleBooleanProperty()
	var hasAwakened by hasAwakenedProperty

	val acquisitionProperty = SimpleObjectProperty<Acquisition>()
	var acquisition by acquisitionProperty

	init {
		this.name = name
		this.slot = slot
		this.tags = FXCollections.observableList(tags)
		this.hasVaal = hasVaal
		this.hasAwakened = hasAwakened
		this.acquisition = acquisition
	}

	fun getFilename(isVaal: Boolean, isAwakened: Boolean): String {
		var output = name.replace(" ", "_")
		if (isVaal && hasVaal)
			output += "[Vaal]"
		if (isAwakened && hasAwakened)
			output += "[Awakened]"
		return "$output.png"
	}

	override fun toString(): String {
		return "GemInfo(name=$name, slot=$slot, tags=$tags, hasVaal=$hasVaal, hasAwakened=$hasAwakened, acquisition=$acquisition)"
	}
}
