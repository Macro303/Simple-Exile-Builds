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
class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<GemInfo?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): GemInfo? {
		val node: JsonNode = jp.codec.readTree(jp)

		val name = node["name"].asText()
		val slot = node["slot"].asText()
		val tags = node["tags"].mapNotNull { GemTag.value(it.asText()) }.sorted()
		val hasVaal = if (node.has("hasVaal")) node["hasVaal"].asBoolean(false) else false
		val hasAwakened = if (node.has("hasAwakened")) node["hasAwakened"].asBoolean(false) else false

		val acquisitionNode = node["acquisition"]
		val recipes = acquisitionNode["recipes"].map { Recipe(it["amount"].asInt(), it["ingredient"].asText()) }
		val quests = acquisitionNode["quests"].map {
			Quest(
				act = it["act"].asInt(),
				quest = it["quest"].asText(),
				classes = it["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
			)
		}
		val vendors = acquisitionNode["vendors"].map {
			Vendor(
				vendor = it["vendor"].asText(),
				act = it["act"].asInt(),
				quest = it["quest"].asText(),
				classes = it["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
			)
		}

		return GemInfo(name, slot, tags, hasVaal, hasAwakened, Acquisition(recipes, quests, vendors))
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}