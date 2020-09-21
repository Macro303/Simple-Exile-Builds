package github.macro.build_info.gems

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class Gem(
	id: String,
	name: String,
	colour: Colour,
	tags: List<String>,
	isVaal: Boolean,
	isSupport: Boolean,
	isAwakened: Boolean,
	acquisition: Acquisition
) {
	val idProperty = SimpleStringProperty()
	var id by idProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val colourProperty = SimpleObjectProperty<Colour>()
	var colour by colourProperty

	val tagsProperty = SimpleListProperty<String>()
	var tags by tagsProperty

	val isVaalProperty = SimpleBooleanProperty()
	var isVaal by isVaalProperty

	val isSupportProperty = SimpleBooleanProperty()
	var isSupport by isSupportProperty

	val isAwakenedProperty = SimpleBooleanProperty()
	var isAwakened by isAwakenedProperty

	val acquisitionProperty = SimpleObjectProperty<Acquisition>()
	var acquisition by acquisitionProperty

	init {
		this.id = id
		this.name = name
		this.colour = colour
		this.tags = FXCollections.observableList(tags)
		this.isVaal = isVaal
		this.isSupport = isSupport
		this.isAwakened = isAwakened
		this.acquisition = acquisition
	}

	fun getFile(): File {
		var baseFolder = File("resources", "Gems")
		if (isSupport) {
			baseFolder = File(baseFolder, "Support")
			if(isAwakened)
				baseFolder = File(baseFolder, "Awakened")
		}else {
			baseFolder = File(baseFolder, "Active")
			if(isVaal)
				baseFolder = File(baseFolder, "Vaal")
		}
		return File(baseFolder, id.substringAfterLast("/") + ".png")
	}

	fun getFullname(): String{
		val prefix = if (isVaal) "Vaal " else if (isAwakened) "Awakened " else ""
		val suffix = if (isSupport) " Support" else ""
		return prefix + name + suffix
	}

	fun getTagname(): String {
		val suffix = if (isVaal) " [Vaal]" else if (isAwakened) " [Awakened]" else ""
		return name + (if (isSupport) " Support" else "") + suffix
	}
}

class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Gem?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Gem? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val colour = Colour.value(node["colour"].asText())
		if (colour == null) {
			LOGGER.info("Invalid Colour: ${node["colour"].asText()}")
			return null
		}
		val tags = node["tags"].mapNotNull { it.asText() }.sorted()
		val isVaal = node["isVaal"]?.asBoolean(false) ?: false
		val isSupport = node["isSupport"]?.asBoolean(false) ?: false
		val isAwakened = node["isAwakened"]?.asBoolean(false) ?: false

		val acquisition = Util.JSON_MAPPER.treeToValue(node["acquisition"], Acquisition::class.java)

		return Gem(
			id = id,
			name = name,
			colour = colour,
			tags = tags,
			isVaal = isVaal,
			isSupport = isSupport,
			isAwakened = isAwakened,
			acquisition = acquisition
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}