package github.macro.core.item.acquisition

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Feb-27.
 */
@JsonDeserialize(using = IngredientDeserializer::class)
class Ingredient(
	val name: String,
	val count: Int,
	val level: Int? = null,
	val quality: Double? = null,
	val ingredientType: IngredientType
)

class IngredientDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Ingredient?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Ingredient? {
		val node: JsonNode = parser.readValueAsTree()

		val name = node["name"].asText()
		val count = node["count"].asInt()
		val level = node["level"]?.asInt()
		val quality = node["quality"]?.asDouble()
		val ingredientType = IngredientType.value(node["type"].asText())
		if (ingredientType == null) {
			LOGGER.warn("Invalid Ingredient Type: ${node["type"].asText()}")
			return null
		}

		return Ingredient(name = name, count = count, level = level, quality = quality, ingredientType = ingredientType)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(IngredientDeserializer::class.java)
	}
}