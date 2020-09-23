package github.macro.core.items.belts

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.core.IItem
import github.macro.core.items.ItemBase
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
@JsonDeserialize(using = ItemBeltDeserializer::class)
class ItemBelt(
	id: String,
	name: String,
	isReleased: Boolean,
	isUnique: Boolean
) : ItemBase(id = id, name = name, isReleased = isReleased, isUnique = isUnique, level = 0, quality = 0.0), IItem

private class ItemBeltDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<ItemBelt?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): ItemBelt? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false
		val isUnique = node["isUnique"]?.asBoolean(false) ?: false

		return ItemBelt(
			id = id,
			name = name,
			isReleased = isReleased,
			isUnique = isUnique
		)
	}
}