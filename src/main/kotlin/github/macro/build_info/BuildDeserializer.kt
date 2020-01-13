package github.macro.build_info

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.build_info.gems.GemDeserializer
import github.macro.build_info.gems.UpdateGem
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BuildInfo?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildInfo? {
		val node: JsonNode = parser.codec.readTree(parser)

		val name = node["name"].asText()
		val classTag = ClassTag.valueOf(node["class"].asText().toUpperCase())
		val ascendency = if (node["ascendency"].isNull)
			null
		else
			Ascendency.valueOf(node["ascendency"].asText().toUpperCase())
		val links = node["links"].toList().map { link -> link.toList().map { Util.textToGem(it.asText()) } }
		val updates = node["updates"].map {
			val oldGem = it["oldGem"].asText()
			val newGem = it["newGem"].asText()
			val reason = it["reason"].asText()
			UpdateGem(oldGem, newGem, reason)
		}

		return BuildInfo(name, classTag, ascendency, links, updates)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}