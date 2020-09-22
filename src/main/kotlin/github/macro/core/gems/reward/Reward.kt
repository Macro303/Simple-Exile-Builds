package github.macro.core.gems.reward

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.core.build_info.ClassTag
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Feb-27.
 */
@JsonDeserialize(using = RewardDeserializer::class)
class Reward(
	act: Int,
	classes: List<ClassTag>,
	quest: String,
	rewardType: RewardType,
	vendor: String?
) {
	val actProperty = SimpleIntegerProperty()
	var act by actProperty

	val classesProperty = SimpleListProperty<ClassTag>()
	var classes by classesProperty

	val questProperty = SimpleStringProperty()
	var quest by questProperty

	val rewardTypeProperty = SimpleObjectProperty<RewardType>()
	var rewardType by rewardTypeProperty

	val vendorProperty = SimpleStringProperty()
	var vendor by vendorProperty

	init {
		this.act = act
		this.classes = FXCollections.observableList(classes)
		this.quest = quest
		this.rewardType = rewardType
		this.vendor = vendor
	}

	override fun toString(): String {
		return "Reward(actProperty=$actProperty, classesProperty=$classesProperty, questProperty=$questProperty, rewardTypeProperty=$rewardTypeProperty, vendorProperty=$vendorProperty)"
	}
}

class RewardDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Reward?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Reward? {
		val node: JsonNode = parser.readValueAsTree()

		val act = node["act"].asInt()
		val classes = node["classes"].mapNotNull { tag -> ClassTag.value(tag.asText()) }.sorted()
		val quest = node["quest"].asText()
		val rewardType = RewardType.value(node["type"].asText())
		if (rewardType == null) {
			LOGGER.warn("Invalid Reward Type: ${node["type"].asText()}")
			return null
		}
		val vendor = node["vendor"]?.asText()

		return Reward(
			act = act,
			classes = classes,
			quest = quest,
			rewardType = rewardType,
			vendor = vendor
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(RewardDeserializer::class.java)
	}
}