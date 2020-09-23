package github.macro.core.items.rings

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
@JsonDeserialize(using = BuildRingDeserializer::class)
@JsonSerialize(using = BuildRingSerializer::class)
class BuildRing(
	item: ItemRing,
	nextItem: BuildRing? = null,
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

private class BuildRingDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildRing?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildRing? {
		val node: JsonNode = parser.codec.readTree(parser)

		val ring = Data.getRingById(node["Item"].asText())
		val nextRing = if (node["Next Item"].isNull)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Item"], BuildRing::class.java)
		val reason = node["Reason"]?.asText()

		return BuildRing(
			item = ring,
			nextItem = nextRing,
			reason = reason
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildRingSerializer @JvmOverloads constructor(t: Class<BuildRing>? = null) :
	StdSerializer<BuildRing>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildRing, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", value.item.id)
		parser.writeObjectField("Next Item", (value.nextItem as BuildRing?))
		parser.writeStringField("Reason", value.reason)
		parser.writeEndObject()
	}
}