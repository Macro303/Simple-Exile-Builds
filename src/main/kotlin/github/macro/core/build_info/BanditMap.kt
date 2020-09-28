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
import javafx.beans.property.SimpleBooleanProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BanditMapDeserializer::class)
@JsonSerialize(using = BanditMapSerializer::class)
class BanditMap(
	kraityn: Boolean,
	alira: Boolean,
	oak: Boolean
) {
	val kraitynProperty = SimpleBooleanProperty()
	var kraityn by kraitynProperty

	val aliraProperty = SimpleBooleanProperty()
	var alira by aliraProperty

	val oakProperty = SimpleBooleanProperty()
	var oak by oakProperty

	init {
		this.kraityn = kraityn
		this.alira = alira
		this.oak = oak
	}
}

private class BanditMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BanditMap?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BanditMap? {
		val node: JsonNode = parser.codec.readTree(parser)

		val kraityn = node["Kraityn"]?.asBoolean() ?: true
		val alira = node["Alira"]?.asBoolean() ?: true
		val oak = node["Oak"]?.asBoolean() ?: true

		return BanditMap(
			kraityn = kraityn,
			alira = alira,
			oak = oak
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BanditMapSerializer @JvmOverloads constructor(t: Class<BanditMap>? = null) : StdSerializer<BanditMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BanditMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeBooleanField("Kraityn", value.kraityn)
		parser.writeBooleanField("Alira", value.alira)
		parser.writeBooleanField("Oak", value.oak)
		parser.writeEndObject()
	}
}