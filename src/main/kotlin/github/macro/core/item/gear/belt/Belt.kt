package github.macro.core.item.gear.belt

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.core.item.gear.BaseGear
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
@JsonDeserialize(using = BeltDeserializer::class)
class Belt(
	id: String,
	name: String,
	isReleased: Boolean,
	isUnique: Boolean
) : BaseGear(id = id, name = name, isReleased = isReleased, isUnique = isUnique)

private class BeltDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Belt?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Belt {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false
		val isUnique = node["isUnique"]?.asBoolean(false) ?: false

		return Belt(
			id = id,
			name = name,
			isReleased = isReleased,
			isUnique = isUnique
		)
	}
}