package github.macro.core.item.acquisition

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.core.build_info.ClassTag
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Feb-27.
 */
@JsonDeserialize(using = RewardDeserializer::class)
class Reward(
	val act: Int,
	val classes: List<ClassTag>,
	val quest: String,
	val method: MethodType,
	val vendor: String? = null
)

class RewardDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Reward?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Reward? {
		val node: JsonNode = parser.readValueAsTree()

		val act = node["act"].asInt()
		val classes = node["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
		val quest = node["quest"].asText()
		val method = MethodType.value(node["method"].asText())
		if (method == null) {
			LOGGER.warn("Invalid Method: ${node["method"].asText()}")
			return null
		}
		val vendor = node["vendor"]?.asText()

		return Reward(
			act = act,
			classes = classes,
			quest = quest,
			method = method,
			vendor = vendor
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(RewardDeserializer::class.java)
	}
}