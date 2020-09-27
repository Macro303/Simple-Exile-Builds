package github.macro.core.item.gear

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import github.macro.core.item.BaseBuildItem
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-24
 */
abstract class BaseBuildGear(
	gear: BaseGear,
	nextGear: BaseBuildGear? = null,
	reason: String? = null,
	var preferences: List<String?> = mutableListOf()
) : BaseBuildItem(item = gear, nextItem = nextGear, reason = reason)

class BuildGearSerializer @JvmOverloads constructor(t: Class<BaseBuildGear>? = null) : StdSerializer<BaseBuildGear>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BaseBuildGear, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Gear", value.item.id)
		parser.writeObjectField("Next Gear", value.nextItem)
		parser.writeStringField("Reason", value.reason)
		parser.writeObjectField("Preferences", value.preferences)
		parser.writeEndObject()
	}
}