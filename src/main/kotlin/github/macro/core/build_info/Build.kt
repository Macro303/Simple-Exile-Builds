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
import github.macro.Util
import github.macro.Util.cleanName
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildDeserializer::class)
@JsonSerialize(using = BuildSerializer::class)
class Build(
	var version: String,
	var name: String,
	var classTag: ClassTag,
	var ascendency: Ascendency,
	val buildGems: BuildGemMap,
	val buildGear: BuildGearMap,
	val bandits: BanditMap,
	val pantheon: PantheonMap,
	details: String?
) {
	val detailsProperty = SimpleStringProperty()
	var details by detailsProperty

	init{
		this.details = details
	}

	val filename: String
		get() = "{$version}_${name.replace(" ", "_")}.yaml"

	val display: String
		get() = "{$version} $name [${classTag.cleanName()}/${ascendency.cleanName()}]"

	fun save() {
		try {
			val buildFile = File("builds", filename)
			Util.YAML_MAPPER.writeValue(buildFile, this)
		} catch (ioe: IOException) {
			LOGGER.error("Unable to save build: $ioe")
		}
	}

	fun delete() {
		val buildFile = File("builds", filename)
		try {
			buildFile.delete()
		} catch (ioe: IOException) {
			LOGGER.error("Unable to delete build: $ioe")
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Build?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Build? {
		val node: JsonNode = parser.codec.readTree(parser)

		val version = node["Version"]?.asText() ?: "0.0.0"
		val name = node["Name"]?.asText() ?: "Build Error"
		val classTag = ClassTag.value(node["Class"]?.asText())
		if (classTag == null) {
			LOGGER.info("Invalid Class: ${node["Class"]?.asText()}")
			return null
		}
		val ascendency = Ascendency.value(node["Ascendency"]?.asText())
		if (ascendency == null) {
			LOGGER.info("Invalid Ascendency: ${node["Ascendency"]?.asText()}")
			return null
		}
		val buildGems = Util.YAML_MAPPER.treeToValue(node["Gems"], BuildGemMap::class.java)
		val buildGear = Util.YAML_MAPPER.treeToValue(node["Gear"], BuildGearMap::class.java)
		val bandits = Util.YAML_MAPPER.treeToValue(node["Bandits"], BanditMap::class.java)
		val pantheon = Util.YAML_MAPPER.treeToValue(node["Pantheon"], PantheonMap::class.java)
		val details = if (node["Details"]?.isNull != false) null else node["Details"]?.asText()

		return Build(
			version = version,
			name = name,
			classTag = classTag,
			ascendency = ascendency,
			buildGems = buildGems,
			buildGear = buildGear,
			bandits = bandits,
			pantheon = pantheon,
			details = details
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildSerializer @JvmOverloads constructor(t: Class<Build>? = null) : StdSerializer<Build>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Build, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Version", value.version)
		parser.writeStringField("Name", value.name)
		parser.writeStringField("Class", value.classTag.name)
		parser.writeStringField("Ascendency", value.ascendency.name)
		parser.writeObjectField("Gems", value.buildGems)
		parser.writeObjectField("Gear", value.buildGear)
		parser.writeObjectField("Bandits", value.bandits)
		parser.writeObjectField("Pantheon", value.pantheon)
		parser.writeObjectField("Details", value.details)
		parser.writeEndObject()
	}
}