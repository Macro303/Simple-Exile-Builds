package github.macro.build_info.gems

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.build_info.ClassTag
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Gem?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Gem? {
		val node: JsonNode = parser.readValueAsTree()

		val name = node["name"].asText()
		val slot = Slot.value(node["slot"].asText()) ?: return null
		val tags = node["tags"].mapNotNull { GemTag.value(it.asText()) }.sorted()
		val isVaal = if (node.has("isVaal")) node["isVaal"].asBoolean(false) else false
		val isAwakened = if (node.has("isAwakened")) node["isAwakened"].asBoolean(false) else false

		val acquisitionNode = node["acquisition"]
		val recipes = acquisitionNode["recipes"].mapNotNull {
			Recipe(
				amount = it["amount"].asInt(),
				ingredient = it["ingredient"].asText()
			)
		}
		val quests = acquisitionNode["quests"].mapNotNull {
			Quest(
				act = it["act"].asInt(),
				quest = it["quest"].asText(),
				classes = it["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
			)
		}
		val vendors = acquisitionNode["vendors"].mapNotNull {
			Vendor(
				vendor = it["vendor"].asText(),
				act = it["act"].asInt(),
				quest = it["quest"].asText(),
				classes = it["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
			)
		}

		return Gem(name, slot, tags, isVaal, isAwakened, Acquisition(recipes, quests, vendors))
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}