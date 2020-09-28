package github.macro.core.build_info

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
import github.macro.core.build_info.pantheon.MajorPantheon
import github.macro.core.build_info.pantheon.MinorPantheon
import javafx.beans.property.SimpleObjectProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-28
 */
@JsonDeserialize(using = PantheonMapDeserializer::class)
@JsonSerialize(using = PantheonMapSerializer::class)
class PantheonMap(
	major: MajorPantheon?,
	minor: MinorPantheon?
) {
	val majorProperty = SimpleObjectProperty<MajorPantheon?>()
	var major by majorProperty

	val minorProperty = SimpleObjectProperty<MinorPantheon?>()
	var minor by minorProperty

	init {
		this.major = major
		this.minor = minor
	}
}

private class PantheonMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<PantheonMap?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): PantheonMap? {
		val node: JsonNode = parser.codec.readTree(parser)

		val major = MajorPantheon.value(node["Major"]?.asText())
		val minor = MinorPantheon.value(node["Minor"]?.asText())

		return PantheonMap(
			major = major,
			minor = minor
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class PantheonMapSerializer @JvmOverloads constructor(t: Class<PantheonMap>? = null) :
	StdSerializer<PantheonMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: PantheonMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Major", value.major?.name)
		parser.writeStringField("Minor", value.minor?.name)
		parser.writeEndObject()
	}
}