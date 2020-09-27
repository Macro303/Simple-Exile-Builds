package github.macro.core.item.gem

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.core.item.acquisition.Acquisition
import github.macro.core.item.BaseItem
import github.macro.core.Colour
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class Gem(
	id: String,
	name: String,
	isReleased: Boolean,
	colour: Colour,
	val tags: List<String>,
	val isVaal: Boolean,
	val isSupport: Boolean,
	val isAwakened: Boolean,
	val acquisition: Acquisition
) : BaseItem(id = id, name = name, isReleased = isReleased, colour = colour) {

	override fun getDisplayName(): String {
		val prefix = if (isVaal) "Vaal " else if (isAwakened) "Awakened " else ""
		val suffix = if (isSupport) " Support" else ""
		return prefix + name + suffix
	}
}

private class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Gem?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Gem? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false
		val colour = Colour.value(node["colour"]?.asText())
		val tags = node["tags"].mapNotNull { it?.asText() }.sorted()
		val isVaal = node["isVaal"]?.asBoolean(false) ?: false
		val isSupport = node["isSupport"]?.asBoolean(false) ?: false
		val isAwakened = node["isAwakened"]?.asBoolean(false) ?: false

		val acquisition = Util.JSON_MAPPER.treeToValue(node["acquisition"], Acquisition::class.java)

		return Gem(
			id = id,
			name = name,
			isReleased = isReleased,
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