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
class BuildDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Build?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Build? {
		val node: JsonNode = parser.codec.readTree(parser)

		val name = node["name"].asText()
		val classTag = ClassTag.value(node["class"].asText()) ?: return null
		val ascendency = Ascendency.value(node["ascendency"].asText()) ?: return null
		val links = node["gems"]["links"].map { link -> link.map { Util.gemByName(it.asText()) } }
		val updates = node["gems"]["updates"].map {
			val oldGem = Util.gemByName(it["oldGem"].asText())
			val newGem = Util.gemByName(it["newGem"].asText())
			val reason = it["reason"].asText()
			UpdateGem(oldGem, newGem, reason)
		}
		val equipment = node["equipment"].mapNotNull { Util.equipmentByName(it.asText()) }

		return Build(name, classTag, ascendency,
			GemBuild(links, updates), equipment)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}