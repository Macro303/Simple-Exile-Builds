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
import github.macro.build_info.equipment.Equipment
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Aug-18
 */
@JsonDeserialize(using = BuildEquipmentDeserializer::class)
@JsonSerialize(using = BuildEquipmentSerializer::class)
class BuildEquipment(
	weapons: List<Equipment>,
	armour: Equipment,
	helmet: Equipment,
	gloves: Equipment,
	boots: Equipment,
	belt: Equipment,
	amulet: Equipment,
	rings: List<Equipment>,
	flasks: List<Equipment>
) {
	val weaponsProperty = SimpleListProperty<Equipment>()
	var weapons by weaponsProperty

	val armourProperty = SimpleObjectProperty<Equipment>()
	var armour by armourProperty

	val helmetProperty = SimpleObjectProperty<Equipment>()
	var helmet by helmetProperty

	val glovesProperty = SimpleObjectProperty<Equipment>()
	var gloves by glovesProperty

	val bootsProperty = SimpleObjectProperty<Equipment>()
	var boots by bootsProperty

	val beltProperty = SimpleObjectProperty<Equipment>()
	var belt by beltProperty

	val amuletProperty = SimpleObjectProperty<Equipment>()
	var amulet by amuletProperty

	val ringsProperty = SimpleListProperty<Equipment>()
	var rings by ringsProperty

	val flasksProperty = SimpleListProperty<Equipment>()
	var flasks by flasksProperty

	init {
		this.weapons = FXCollections.observableList(weapons)
		this.armour = armour
		this.helmet = helmet
		this.gloves = gloves
		this.boots = boots
		this.belt = belt
		this.amulet = amulet
		this.rings = FXCollections.observableList(rings)
		this.flasks = FXCollections.observableList(flasks)
	}

	override fun toString(): String {
		return "BuildEquipment(weaponsProperty=$weaponsProperty, armourProperty=$armourProperty, helmetProperty=$helmetProperty, glovesProperty=$glovesProperty, bootsProperty=$bootsProperty, beltProperty=$beltProperty, amuletProperty=$amuletProperty, ringsProperty=$ringsProperty, flasksProperty=$flasksProperty)"
	}
}

class BuildEquipmentDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildEquipment?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildEquipment? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.equipmentByName(it.asText()) }?.chunked(2)?.firstOrNull()
			?: emptyList()
		val armour = Util.equipmentByName(node["Armour"]?.asText())
		val helmet = Util.equipmentByName(node["Helmet"]?.asText())
		val gloves = Util.equipmentByName(node["Gloves"]?.asText())
		val boots = Util.equipmentByName(node["Boots"]?.asText())
		val belt = Util.equipmentByName(node["Belt"]?.asText())
		val amulet = Util.equipmentByName(node["Amulet"]?.asText())
		val rings = node["Rings"]?.map { Util.equipmentByName(it.asText()) }?.chunked(2)?.firstOrNull()
			?: emptyList()
		val flasks = node["Flasks"]?.map { Util.equipmentByName(it.asText()) }?.chunked(5)?.firstOrNull()
			?: emptyList()

		return BuildEquipment(
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

class BuildEquipmentSerializer @JvmOverloads constructor(t: Class<BuildEquipment>? = null) :
	StdSerializer<BuildEquipment>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildEquipment, parser: JsonGenerator, provider: SerializerProvider?) {
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