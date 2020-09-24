package github.macro.core.items.weapons

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
@JsonDeserialize(using = BuildWeaponDeserializer::class)
@JsonSerialize(using = BuildWeaponSerializer::class)
class BuildWeapon(
	item: Weapon,
	nextItem: BuildWeapon? = null,
	reason: String? = null,
	preferences: List<String?> = mutableListOf()
) : BuildItemBase(item = item, nextItem = nextItem, reason = reason, preferences = preferences), IBuildItem

private class BuildWeaponDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildWeapon?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildWeapon? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapon = Data.getWeaponById(node["Item"].asText())
		val nextWeapon = if (node["Next Item"].isNull)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Item"], BuildWeapon::class.java)
		val reason = node["Reason"]?.asText()
		val preferences = node["Preferences"]?.map { it?.asText() }?.chunked(3)?.firstOrNull() ?: mutableListOf()

		return BuildWeapon(
			item = weapon,
			nextItem = nextWeapon,
			reason = reason,
			preferences = preferences
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildWeaponSerializer @JvmOverloads constructor(t: Class<BuildWeapon>? = null) :
	StdSerializer<BuildWeapon>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildWeapon, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", value.item.id)
		parser.writeObjectField("Next Item", (value.nextItem as BuildWeapon?))
		parser.writeStringField("Reason", value.reason)
		parser.writeObjectField("Preferences", value.preferences)
		parser.writeEndObject()
	}
}