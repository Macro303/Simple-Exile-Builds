package github.macro.build_info

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
import github.macro.Data
import github.macro.Util
import github.macro.build_info.gems.Gem
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonSerialize(using = UpdateSerializer::class)
@JsonDeserialize(using = UpdateDeserializer::class)
class Update(
	old: Gem,
	new: Gem,
	reason: String
) {
	val oldProperty = SimpleObjectProperty<Gem>()
	var old by oldProperty

	val newProperty = SimpleObjectProperty<Gem>()
	var new by newProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		this.old = old
		this.new = new
		this.reason = reason
	}

	override fun toString(): String {
		return "Update(oldProperty=$oldProperty, newProperty=$newProperty, reasonProperty=$reasonProperty)"
	}
}

class UpdateDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Update?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Update? {
		val node: JsonNode = parser.codec.readTree(parser)

		val old = Data.gemByName(node["Old Gem"]?.asText())
		val new = Data.gemByName(node["New Gem"]?.asText())
		val reason = node["Reason"].asText()
		return Update(old = old, new = new, reason = reason)
	}
}

class UpdateSerializer @JvmOverloads constructor(t: Class<Update>? = null) : StdSerializer<Update>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Update, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Old Gem", value.old.getFullname())
		parser.writeStringField("New Gem", value.new.getFullname())
		parser.writeStringField("Reason", value.reason)
		parser.writeEndObject()
	}
}