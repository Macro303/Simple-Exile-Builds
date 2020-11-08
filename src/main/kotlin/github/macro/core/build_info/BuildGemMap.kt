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
import github.macro.core.item.Items
import github.macro.core.item.gem.BuildGem
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildGemMapDeserializer::class)
@JsonSerialize(using = BuildGemMapSerializer::class)
class BuildGemMap(
	var weapons: List<BuildGem>,
	var helmet: List<BuildGem>,
	var bodyArmour: List<BuildGem>,
	var gloves: List<BuildGem>,
	var boots: List<BuildGem>
) {
	init {
		while (weapons.size < 6)
			weapons = weapons.plusElement(BuildGem(Items.getGemById("None")))
		while (helmet.size < 4)
			helmet = helmet.plusElement(BuildGem(Items.getGemById("None")))
		while (bodyArmour.size < 6)
			bodyArmour = bodyArmour.plusElement(BuildGem(Items.getGemById("None")))
		while (gloves.size < 4)
			gloves = gloves.plusElement(BuildGem(Items.getGemById("None")))
		while (boots.size < 4)
			boots = boots.plusElement(BuildGem(Items.getGemById("None")))
	}
}

private class BuildGemMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildGemMap?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGemMap {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val helmet = node["Helmet"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val bodyArmour = node["Body Armour"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val gloves = node["Gloves"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()
		val boots = node["Boots"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildGem::class.java) }
			?.chunked(6)?.firstOrNull() ?: mutableListOf()

		return BuildGemMap(
			weapons = weapons,
			helmet = helmet,
			bodyArmour = bodyArmour,
			gloves = gloves,
			boots = boots
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildGemMapSerializer @JvmOverloads constructor(t: Class<BuildGemMap>? = null) :
	StdSerializer<BuildGemMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGemMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons)
		parser.writeObjectField("Helmet", value.helmet)
		parser.writeObjectField("Body Armour", value.bodyArmour)
		parser.writeObjectField("Gloves", value.gloves)
		parser.writeObjectField("Boots", value.boots)
		parser.writeEndObject()
	}
}