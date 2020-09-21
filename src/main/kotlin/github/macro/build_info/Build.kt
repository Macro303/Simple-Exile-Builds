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
import github.macro.Util
import github.macro.Util.cleanName
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = BuildDeserializer::class)
@JsonSerialize(using = BuildSerializer::class)
class Build(
	version: String,
	name: String,
	classTag: ClassTag,
	ascendency: Ascendency,
	gems: BuildGems,
	gear: BuildGear
) {
	val versionProperty = SimpleStringProperty()
	var version by versionProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val classProperty = SimpleObjectProperty<ClassTag>()
	var classTag by classProperty

	val ascendencyProperty = SimpleObjectProperty<Ascendency>()
	var ascendency by ascendencyProperty

	val gemsProperty = SimpleObjectProperty<BuildGems>()
	var gems by gemsProperty

	val gearProperty = SimpleObjectProperty<BuildGear>()
	var gear by gearProperty

	init {
		this.version = version
		this.name = name
		this.classTag = classTag
		this.ascendency = ascendency
		this.gems = gems
		this.gear = gear
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

	override fun toString(): String {
		return "Build(versionProperty=$versionProperty, nameProperty=$nameProperty, classProperty=$classProperty, ascendencyProperty=$ascendencyProperty, gemsProperty=$gemsProperty, equipmentProperty=$gearProperty)"
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Build::class.java)
	}
}

class BuildDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Build?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Build? {
		val node: JsonNode = parser.codec.readTree(parser)

		val version = node["Version"].asText()
		val name = node["Name"].asText()
		val classTag = ClassTag.value(node["Class"].asText())
		if (classTag == null) {
			LOGGER.info("Invalid Class: ${node["Class"].asText()}")
			return null
		}
		val ascendency = Ascendency.value(node["Ascendency"].asText())
		if (ascendency == null) {
			LOGGER.info("Invalid Ascendency: ${node["Ascendency"].asText()}")
			return null
		}
		val buildGems = Util.YAML_MAPPER.treeToValue(node["Gems"], BuildGems::class.java)
		val gear = Util.YAML_MAPPER.treeToValue(node["Gear"], BuildGear::class.java)

		return Build(
			version = version,
			name = name,
			classTag = classTag,
			ascendency = ascendency,
			gems = buildGems,
			gear = gear
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildDeserializer::class.java)
	}
}

class BuildSerializer @JvmOverloads constructor(t: Class<Build>? = null) : StdSerializer<Build>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Build, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Version", value.version)
		parser.writeStringField("Name", value.name)
		parser.writeStringField("Class", value.classTag.name)
		parser.writeStringField("Ascendency", value.ascendency.name)
		parser.writeObjectField("Gems", value.gems)
		parser.writeObjectField("Gear", value.gear)
		parser.writeEndObject()
	}
}