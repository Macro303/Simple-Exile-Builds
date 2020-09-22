package github.macro.core.items

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
import github.macro.core.items.amulets.BuildAmulet
import github.macro.core.items.body_armours.BuildBodyArmour
import github.macro.core.items.belts.BuildBelt
import github.macro.core.items.boots.BuildBoots
import github.macro.core.items.flasks.BuildFlask
import github.macro.core.items.gloves.BuildGloves
import github.macro.core.items.helmets.BuildHelmet
import github.macro.core.items.rings.BuildRing
import github.macro.core.items.weapons.BuildWeapon
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildGearMapDeserializer::class)
@JsonSerialize(using = BuildGearMapSerializer::class)
class BuildItemMap(
	weapons: List<BuildWeapon>,
	bodyArmour: BuildBodyArmour,
	helmet: BuildHelmet,
	gloves: BuildGloves,
	boots: BuildBoots,
	belt: BuildBelt,
	amulet: BuildAmulet,
	rings: List<BuildRing>,
	flasks: List<BuildFlask>
) {
	val weaponsProperty = SimpleListProperty<BuildWeapon>()
	var weapons by weaponsProperty

	val bodyArmourProperty = SimpleObjectProperty<BuildBodyArmour>()
	var bodyArmour by bodyArmourProperty

	val helmetProperty = SimpleObjectProperty<BuildHelmet>()
	var helmet by helmetProperty

	val glovesProperty = SimpleObjectProperty<BuildGloves>()
	var gloves by glovesProperty

	val bootsProperty = SimpleObjectProperty<BuildBoots>()
	var boots by bootsProperty

	val beltProperty = SimpleObjectProperty<BuildBelt>()
	var belt by beltProperty

	val amuletProperty = SimpleObjectProperty<BuildAmulet>()
	var amulet by amuletProperty

	val ringsProperty = SimpleListProperty<BuildRing>()
	var rings by ringsProperty

	val flasksProperty = SimpleListProperty<BuildFlask>()
	var flasks by flasksProperty

	init {
		var weaponsTemp = weapons
		while (weaponsTemp.size < 2)
			weaponsTemp = weaponsTemp.plus(BuildWeapon(Data.getWeapon("None")))
		this.weapons = FXCollections.observableList(weaponsTemp)
		this.bodyArmour = bodyArmour
		this.helmet = helmet
		this.gloves = gloves
		this.boots = boots
		this.belt = belt
		this.amulet = amulet
		var ringsTemp = rings
		while (ringsTemp.size < 2)
			ringsTemp = ringsTemp.plus(BuildRing(Data.getRing("None")))
		this.rings = FXCollections.observableList(ringsTemp)
		var flasksTemp = flasks
		while (flasksTemp.size < 5)
			flasksTemp = flasksTemp.plus(BuildFlask(Data.getFlask("None")))
		this.flasks = FXCollections.observableList(flasksTemp)
	}
}

private class BuildGearMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildItemMap?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildItemMap? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildWeapon::class.java) }
			?.chunked(2)?.firstOrNull() ?: mutableListOf()
		val bodyArmour = Util.YAML_MAPPER.treeToValue(node["Body Armour"], BuildBodyArmour::class.java)
		val helmet = Util.YAML_MAPPER.treeToValue(node["Helmet"], BuildHelmet::class.java)
		val gloves = Util.YAML_MAPPER.treeToValue(node["Gloves"], BuildGloves::class.java)
		val boots = Util.YAML_MAPPER.treeToValue(node["Boots"], BuildBoots::class.java)
		val belt = Util.YAML_MAPPER.treeToValue(node["Belt"], BuildBelt::class.java)
		val amulet = Util.YAML_MAPPER.treeToValue(node["Amulet"], BuildAmulet::class.java)
		val rings = node["Rings"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildRing::class.java) }
			?.chunked(2)?.firstOrNull() ?: mutableListOf()
		val flasks = node["Flasks"]?.map { Util.YAML_MAPPER.treeToValue(it, BuildFlask::class.java) }
			?.chunked(5)?.firstOrNull() ?: mutableListOf()

		return BuildItemMap(
			weapons = weapons,
			bodyArmour = bodyArmour,
			helmet = helmet,
			gloves = gloves,
			boots = boots,
			belt = belt,
			amulet = amulet,
			rings = rings,
			flasks = flasks
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}

private class BuildGearMapSerializer @JvmOverloads constructor(t: Class<BuildItemMap>? = null) :
	StdSerializer<BuildItemMap>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildItemMap, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons)
		parser.writeObjectField("Body Armour", value.bodyArmour)
		parser.writeObjectField("Helmet", value.helmet)
		parser.writeObjectField("Gloves", value.gloves)
		parser.writeObjectField("Boots", value.boots)
		parser.writeObjectField("Belt", value.belt)
		parser.writeObjectField("Amulet", value.amulet)
		parser.writeObjectField("Rings", value.rings)
		parser.writeObjectField("Flasks", value.flasks)
		parser.writeEndObject()
	}
}