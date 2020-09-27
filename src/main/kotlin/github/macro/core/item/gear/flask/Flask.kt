package github.macro.core.item.gear.flask

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.core.item.acquisition.Acquisition
import github.macro.core.item.gear.BaseGear
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
@JsonDeserialize(using = FlaskDeserializer::class)
class Flask(
	id: String,
	name: String,
	isReleased: Boolean,
	isUnique: Boolean,
	val acquisition: Acquisition
) : BaseGear(id = id, name = name, isReleased = isReleased, isUnique = isUnique)

private class FlaskDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Flask?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Flask? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false
		val isUnique = node["isUnique"]?.asBoolean(false) ?: false

		val acquisition = Util.JSON_MAPPER.treeToValue(node["acquisition"], Acquisition::class.java)

		return Flask(
			id = id,
			name = name,
			isReleased = isReleased,
			isUnique = isUnique,
			acquisition = acquisition
		)
	}
}