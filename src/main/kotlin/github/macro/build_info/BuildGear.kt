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
import github.macro.Data
import github.macro.Util
import github.macro.build_info.flasks.Flask
import github.macro.build_info.gear.Gear
import github.macro.build_info.rings.Ring
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Aug-18
 */
@JsonDeserialize(using = BuildGearDeserializer::class)
@JsonSerialize(using = BuildGearSerializer::class)
class BuildGear(
	weapons: List<Gear>,
	armour: Gear,
	helmet: Gear,
	gloves: Gear,
	boots: Gear,
	belt: Gear,
	amulet: Gear,
	rings: List<Ring>,
	flasks: List<Flask>
) {
	val weaponsProperty = SimpleListProperty<Gear>()
	var weapons by weaponsProperty

	val armourProperty = SimpleObjectProperty<Gear>()
	var armour by armourProperty

	val helmetProperty = SimpleObjectProperty<Gear>()
	var helmet by helmetProperty

	val glovesProperty = SimpleObjectProperty<Gear>()
	var gloves by glovesProperty

	val bootsProperty = SimpleObjectProperty<Gear>()
	var boots by bootsProperty

	val beltProperty = SimpleObjectProperty<Gear>()
	var belt by beltProperty

	val amuletProperty = SimpleObjectProperty<Gear>()
	var amulet by amuletProperty

	val ringsProperty = SimpleListProperty<Ring>()
	var rings by ringsProperty

	val flasksProperty = SimpleListProperty<Flask>()
	var flasks by flasksProperty

	init {
		this.weapons = FXCollections.observableList(weapons)
		this.armour = armour
		this.helmet = helmet
		this.gloves = gloves
		this.boots = boots
		this.belt = belt
		this.amulet = amulet
		var ringsTemp = rings
		while (ringsTemp.size < 2)
			ringsTemp = ringsTemp.plus(Data.MISSING_RING)
		this.rings = FXCollections.observableList(ringsTemp)
		var flasksTemp = flasks
		while (flasksTemp.size < 5)
			flasksTemp = flasksTemp.plus(Data.MISSING_FLASK)
		this.flasks = FXCollections.observableList(flasksTemp)
	}
}

class BuildGearDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildGear?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGear? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Data.gearByName(it.asText()) }?.chunked(2)?.firstOrNull()
			?: mutableListOf()
		val armour = Data.gearByName(node["Armour"]?.asText())
		val helmet = Data.gearByName(node["Helmet"]?.asText())
		val gloves = Data.gearByName(node["Gloves"]?.asText())
		val boots = Data.gearByName(node["Boots"]?.asText())
		val belt = Data.gearByName(node["Belt"]?.asText())
		val amulet = Data.gearByName(node["Amulet"]?.asText())
		val rings = node["Rings"]?.map { Data.ringByName(it.asText()) }?.chunked(2)?.firstOrNull()
			?: mutableListOf()
		val flasks = node["Flasks"]?.map { Data.flaskByName(it.asText()) }?.chunked(5)?.firstOrNull()
			?: mutableListOf()

		return BuildGear(
			weapons = weapons,
			armour = armour,
			helmet = helmet,
			gloves = gloves,
			boots = boots,
			belt = belt,
			amulet = amulet,
			rings = rings,
			flasks = flasks
		)
	}
}

class BuildGearSerializer @JvmOverloads constructor(t: Class<BuildGear>? = null) :
	StdSerializer<BuildGear>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGear, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons.map { it.name })
		parser.writeObjectField("Armour", value.armour.name)
		parser.writeObjectField("Helmet", value.helmet.name)
		parser.writeObjectField("Gloves", value.gloves.name)
		parser.writeObjectField("Boots", value.boots.name)
		parser.writeObjectField("Belt", value.belt.name)
		parser.writeObjectField("Amulet", value.amulet.name)
		parser.writeObjectField("Rings", value.rings.map { it.name })
		parser.writeObjectField("Flasks", value.flasks.map { it.name })
		parser.writeEndObject()
	}
}