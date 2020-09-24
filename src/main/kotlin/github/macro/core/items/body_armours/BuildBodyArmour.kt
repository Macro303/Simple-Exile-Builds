package github.macro.core.items.body_armours

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import github.macro.Util
import github.macro.core.Data
import github.macro.core.IBuildItem
import github.macro.core.items.BuildItemBase
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildBodyArmourDeserializer::class)
@JsonSerialize(using = BuildBodyArmourSerializer::class)
class BuildBodyArmour(
	item: BodyArmour,
	nextItem: BuildBodyArmour? = null,
	reason: String? = null,
	preferences: List<String?> = mutableListOf()
) : BuildItemBase(item = item, nextItem = nextItem, reason = reason, preferences = preferences), IBuildItem

private class BuildBodyArmourDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildBodyArmour?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildBodyArmour? {
		val node: JsonNode = parser.codec.readTree(parser)

		val armour = Data.getBodyArmourById(node["Item"].asText())
		val nextArmour = if (node["Next Item"].isNull)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Item"], BuildBodyArmour::class.java)
		val reason = node["Reason"]?.asText()
		val preferences = node["Preferences"]?.map { it?.asText() }?.chunked(3)?.firstOrNull() ?: mutableListOf()

		return BuildBodyArmour(
			item = armour,
			nextItem = nextArmour,
			reason = reason,
			preferences = preferences
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildBodyArmourSerializer @JvmOverloads constructor(t: Class<BuildBodyArmour>? = null) :
	StdSerializer<BuildBodyArmour>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildBodyArmour, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", value.item.id)
		parser.writeObjectField("Next Item", (value.nextItem as BuildBodyArmour?))
		parser.writeStringField("Reason", value.reason)
		parser.writeObjectField("Preferences", value.preferences)
		parser.writeEndObject()
	}
}