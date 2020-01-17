package github.macro.build_info

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildSerializer @JvmOverloads constructor(t: Class<BuildInfo>? = null) : StdSerializer<BuildInfo>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildInfo, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("name", value.name)
		parser.writeStringField("class", value.classTag.name)
		parser.writeStringField("ascendency", value.ascendency.name)
		parser.writeObjectFieldStart("gems")
		parser.writeObjectField("links", value.links.map { link -> link.map { it?.getFullname() } })
		parser.writeArrayFieldStart("updates")
		value.updates.forEach { update ->
			parser.writeStartObject()
			parser.writeStringField("oldGem", update.oldGem?.getFullname())
			parser.writeStringField("newGem", update.newGem?.getFullname())
			parser.writeStringField("reason", update.reason)
			parser.writeEndObject()
		}
		parser.writeEndArray()
		parser.writeEndObject()
		parser.writeObjectField("equipment", value.equipment.map { it.name })
		parser.writeEndObject()
	}

	companion object{
		private val LOGGER = LogManager.getLogger(BuildSerializer::class.java)
	}
}