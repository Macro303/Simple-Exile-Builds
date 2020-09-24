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
import github.macro.core.gems.BuildGemMap
import github.macro.core.items.BuildItemMap
import javafx.beans.property.SimpleObjectProperty
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
	version: String,
	name: String,
	classTag: ClassTag,
	ascendency: Ascendency,
	buildGems: BuildGemMap,
	buildItems: BuildItemMap,
	details: String?
) {
	val versionProperty = SimpleStringProperty()
	var version by versionProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val classProperty = SimpleObjectProperty<ClassTag>()
	var classTag by classProperty

	val ascendencyProperty = SimpleObjectProperty<Ascendency>()
	var ascendency by ascendencyProperty

	val buildGemsProperty = SimpleObjectProperty<BuildGemMap>()
	var buildGems by buildGemsProperty

	val buildItemsProperty = SimpleObjectProperty<BuildItemMap>()
	var buildItems by buildItemsProperty

	val detailsProperty = SimpleStringProperty()
	var details by detailsProperty

	init {
		this.version = version
		this.name = name
		this.classTag = classTag
		this.ascendency = ascendency
		this.buildGems = buildGems
		this.buildItems = buildItems
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
		val buildGems = Util.YAML_MAPPER.treeToValue(node["Gems"], BuildGemMap::class.java)
		val buildItems = Util.YAML_MAPPER.treeToValue(node["Items"], BuildItemMap::class.java)
		val details = node["Details"]?.asText()

		return Build(
			version = version,
			name = name,
			classTag = classTag,
			ascendency = ascendency,
			buildGems = buildGems,
			buildItems = buildItems,
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
		parser.writeObjectField("Items", value.buildItems)
		parser.writeStringField("Details", value.details)
		parser.writeEndObject()
	}
}