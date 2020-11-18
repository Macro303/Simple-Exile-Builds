package github.macro.core.item

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
abstract class BaseBuildItem(
	var item: BaseItem,
	var nextItem: BaseBuildItem? = null,
	var reason: String? = null
) {
	override fun toString(): String {
		return "BaseBuildItem(item=$item, nextItem=$nextItem, reason=$reason)"
	}
}

class BuildItemSerializer @JvmOverloads constructor(t: Class<BaseBuildItem>? = null) : StdSerializer<BaseBuildItem>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BaseBuildItem, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", if (value.item.id == "Missing") value.item.name else value.item.id)
		parser.writeObjectField("Next Item", value.nextItem)
		parser.writeStringField("Reason", value.reason)
		parser.writeEndObject()
	}
}