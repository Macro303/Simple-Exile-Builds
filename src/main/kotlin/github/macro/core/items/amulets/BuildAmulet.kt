package github.macro.core.items.amulets

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
@JsonDeserialize(using = BuildAmuletDeserializer::class)
@JsonSerialize(using = BuildAmuletSerializer::class)
class BuildAmulet(
	item: ItemAmulet,
	nextItem: BuildAmulet? = null,
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

private class BuildAmuletDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildAmulet?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildAmulet? {
		val node: JsonNode = parser.codec.readTree(parser)

		val amulet = Data.getAmuletById(node["Item"].asText())
		val nextAmulet = if (node["Next Item"].isNull)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Item"], BuildAmulet::class.java)
		val reason = node["Reason"]?.asText()

		return BuildAmulet(
			item = amulet,
			nextItem = nextAmulet,
			reason = reason
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildAmuletSerializer @JvmOverloads constructor(t: Class<BuildAmulet>? = null) :
	StdSerializer<BuildAmulet>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildAmulet, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Item", value.item.id)
		parser.writeObjectField("Next Item", (value.nextItem as BuildAmulet?))
		parser.writeStringField("Reason", value.reason)
		parser.writeEndObject()
	}
}