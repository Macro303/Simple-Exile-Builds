package github.macro.core.gems

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
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildGemMapDeserializer::class)
@JsonSerialize(using = BuildGemMapSerializer::class)
class BuildGemMap(
	weapons: List<BuildGem>,
	bodyArmour: List<BuildGem>,
	helmet: List<BuildGem>,
	gloves: List<BuildGem>,
	boots: List<BuildGem>
) {
	val weaponsProperty = SimpleListProperty<BuildGem>()
	var weapons by weaponsProperty

	val bodyArmourProperty = SimpleListProperty<BuildGem>()
	var bodyArmour by bodyArmourProperty

	val helmetProperty = SimpleListProperty<BuildGem>()
	var helmet by helmetProperty

	val glovesProperty = SimpleListProperty<BuildGem>()
	var gloves by glovesProperty

	val bootsProperty = SimpleListProperty<BuildGem>()
	var boots by bootsProperty

	init {
		var weaponsTemp = weapons
		while (weaponsTemp.size < 6)
			weaponsTemp = weaponsTemp.plus(BuildGem(Data.getGemByName("None")))
		this.weapons = FXCollections.observableList(weaponsTemp)
		var bodyArmourTemp = bodyArmour
		while (bodyArmourTemp.size < 6)
			bodyArmourTemp = bodyArmourTemp.plus(BuildGem(Data.getGemByName("None")))
		this.bodyArmour = FXCollections.observableList(bodyArmourTemp)
		var helmetTemp = helmet
		while (helmetTemp.size < 4)
			helmetTemp = helmetTemp.plus(BuildGem(Data.getGemByName("None")))
		this.helmet = FXCollections.observableList(helmetTemp)
		var glovesTemp = gloves
		while (glovesTemp.size < 4)
			glovesTemp = glovesTemp.plus(BuildGem(Data.getGemByName("None")))
		this.gloves = FXCollections.observableList(glovesTemp)
		var bootsTemp = boots
		while (bootsTemp.size < 4)
			bootsTemp = bootsTemp.plus(BuildGem(Data.getGemByName("None")))
		this.boots = FXCollections.observableList(bootsTemp)
	}
}

private class BuildGemMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BuildGemMap?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGemMap? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val bodyArmour = node["Body Armour"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val helmet = node["Helmet"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val gloves = node["Gloves"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val boots = node["Boots"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()

		return BuildGemMap(
			weapons = weapons,
			bodyArmour = bodyArmour,
			helmet = helmet,
			gloves = gloves,
			boots = boots
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildGemMapSerializer @JvmOverloads constructor(t: Class<BuildGemMap>? = null) : StdSerializer<BuildGemMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGemMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons)
		parser.writeObjectField("Body Armour", value.bodyArmour)
		parser.writeObjectField("Helmet", value.helmet)
		parser.writeObjectField("Gloves", value.gloves)
		parser.writeObjectField("Boots", value.boots)
		parser.writeEndObject()
	}
}