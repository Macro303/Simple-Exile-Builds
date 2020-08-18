package github.macro.build_info.gems.ingredient

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.build_info.BuildDeserializer
import github.macro.build_info.gems.reward.RewardDeserializer
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Feb-27.
 */
@JsonDeserialize(using = IngredientDeserializer::class)
class Ingredient(
	name: String,
	count: Int,
	level: Int?,
	quality: Double?,
	ingredientType: IngredientType
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val countProperty = SimpleIntegerProperty()
	var count by countProperty

	val levelProperty = SimpleObjectProperty<Int?>()
	var level by levelProperty

	val qualityProperty = SimpleObjectProperty<Double?>()
	var quality by qualityProperty

	val ingredientTypeProperty = SimpleObjectProperty<IngredientType>()
	var ingredientType by ingredientTypeProperty

	init {
		this.name = name
		this.count = count
		this.level = level
		this.quality = quality
		this.ingredientType = ingredientType
	}

	override fun toString(): String {
		return "Ingredient(nameProperty=$nameProperty, countProperty=$countProperty, levelProperty=$levelProperty, qualityProperty=$qualityProperty, ingredientTypeProperty=$ingredientTypeProperty)"
	}
}

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