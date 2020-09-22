package github.macro.core.items.belts

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
import github.macro.core.IItem
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildBeltDeserializer::class)
@JsonSerialize(using = BuildBeltSerializer::class)
class BuildBelt(
	item: ItemBelt,
	nextItem: BuildBelt? = null,
	reason: String? = null
) : IBuildItem {
	val itemProperty = SimpleObjectProperty<IItem>()
	override var item by itemProperty

	val nextItemProperty = SimpleObjectProperty<IBuildItem?>()
	override var nextItem by nextItemProperty

	val reasonProperty = SimpleStringProperty()
	override var reason by reasonProperty

	init {
		this.item = item
		this.nextItem = nextItem
		this.reason = reason
	}
}

private class BuildBeltDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildBelt?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildBelt? {
		val node: JsonNode = parser.codec.readTree(parser)

		val belt = Data.getBelt(node["Item"].asText())
		val nextBelt = if (node["Next Item"].isNull)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Item"], BuildBelt::class.java)
		val reason = node["Reason"]?.asText()

		return BuildBelt(
			item = belt,
			nextItem = nextBelt,
			reason = reason
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildBeltSerializer @JvmOverloads constructor(t: Class<BuildBelt>? = null) :
	StdSerializer<BuildBelt>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildBelt, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", (value.item as ItemBelt).name)
		parser.writeObjectField("Next Item", (value.nextItem as BuildBelt?))
		parser.writeStringField("Reason", value.reason)
		parser.writeEndObject()
	}
}