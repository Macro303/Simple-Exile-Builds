package github.macro.build_info.flasks

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
@JsonDeserialize(using = FlaskDeserializer::class)
class Flask(
	id: String,
	name: String,
	isUnique: Boolean,
	isReleased: Boolean
) {
	val idProperty = SimpleStringProperty()
	var id by idProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val isUniqueProperty = SimpleBooleanProperty()
	var isUnique by isUniqueProperty

	val isReleasedProperty = SimpleBooleanProperty()
	var isReleased by isReleasedProperty

	init {
		this.id = id
		this.name = name
		this.isUnique = isUnique
		this.isReleased = isReleased
	}

	fun getFile(): File {
		var baseFolder = File("resources", "Flasks")
		if (isUnique)
			baseFolder = File(baseFolder, "Unique")
		else
			baseFolder = File(baseFolder, "Basic")
		return File(baseFolder, id.substringAfterLast("/") + ".png")
	}

	override fun toString(): String {
		return "Flask(idProperty=$idProperty, nameProperty=$nameProperty, isUniqueProperty=$isUniqueProperty, isReleasedProperty=$isReleasedProperty)"
	}

}

class FlaskDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Flask?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Flask? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isUnique = node["isUnique"]?.asBoolean(false) ?: false
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false

		return Flask(
			id = id,
			name = name,
			isUnique = isUnique,
			isReleased = isReleased
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(FlaskDeserializer::class.java)
	}
}