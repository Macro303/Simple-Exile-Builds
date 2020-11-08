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
import github.macro.core.item.gear.amulet.BuildAmulet
import github.macro.core.item.gear.belt.BuildBelt
import github.macro.core.item.gear.body_armour.BuildBodyArmour
import github.macro.core.item.gear.boots.BuildBoots
import github.macro.core.item.gear.flask.BuildFlask
import github.macro.core.item.gear.gloves.BuildGloves
import github.macro.core.item.gear.helmets.BuildHelmet
import github.macro.core.item.gear.rings.BuildRing
import github.macro.core.item.gear.weapons.BuildWeapon
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildGearMapDeserializer::class)
@JsonSerialize(using = BuildGearMapSerializer::class)
class BuildGearMap(
	var weapons: ArrayList<BuildWeapon>,
	var helmet: BuildHelmet,
	var bodyArmour: BuildBodyArmour,
	var gloves: BuildGloves,
	var boots: BuildBoots,
	var belt: BuildBelt,
	var amulet: BuildAmulet,
	var rings: ArrayList<BuildRing>,
	var flasks: ArrayList<BuildFlask>
) {
	init {
		while (weapons.size < 2)
			weapons.add(BuildWeapon(Items.getWeaponById("None")))
		while (rings.size < 2)
			rings.add(BuildRing(Items.getRingById("None")))
		while (flasks.size < 5)
			flasks.add(BuildFlask(Items.getFlaskById("None")))
	}
}

private class BuildGearMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildGearMap?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGearMap {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildWeapon::class.java) }
			?.chunked(2)?.firstOrNull() ?: mutableListOf()
		val helmet = Util.YAML_MAPPER.treeToValue(node["Helmet"], BuildHelmet::class.java)
		val bodyArmour = Util.YAML_MAPPER.treeToValue(node["Body Armour"], BuildBodyArmour::class.java)
		val gloves = Util.YAML_MAPPER.treeToValue(node["Gloves"], BuildGloves::class.java)
		val boots = Util.YAML_MAPPER.treeToValue(node["Boots"], BuildBoots::class.java)
		val belt = Util.YAML_MAPPER.treeToValue(node["Belt"], BuildBelt::class.java)
		val amulet = Util.YAML_MAPPER.treeToValue(node["Amulet"], BuildAmulet::class.java)
		val rings = node["Rings"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildRing::class.java) }
			?.chunked(2)?.firstOrNull() ?: mutableListOf()
		val flasks = node["Flasks"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildFlask::class.java) }
			?.chunked(5)?.firstOrNull() ?: mutableListOf()

		return BuildGearMap(
			weapons = weapons as ArrayList<BuildWeapon>,
			helmet = helmet,
			bodyArmour = bodyArmour,
			gloves = gloves,
			boots = boots,
			belt = belt,
			amulet = amulet,
			rings = rings as ArrayList<BuildRing>,
			flasks = flasks as ArrayList<BuildFlask>
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildGearMapSerializer @JvmOverloads constructor(t: Class<BuildGearMap>? = null) :
	StdSerializer<BuildGearMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGearMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons)
		parser.writeObjectField("Helmet", value.helmet)
		parser.writeObjectField("Body Armour", value.bodyArmour)
		parser.writeObjectField("Gloves", value.gloves)
		parser.writeObjectField("Boots", value.boots)
		parser.writeObjectField("Belt", value.belt)
		parser.writeObjectField("Amulet", value.amulet)
		parser.writeObjectField("Rings", value.rings)
		parser.writeObjectField("Flasks", value.flasks)
		parser.writeEndObject()
	}
}