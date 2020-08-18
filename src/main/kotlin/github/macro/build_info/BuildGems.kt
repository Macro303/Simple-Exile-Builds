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
import github.macro.build_info.gems.Gem
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-23.
 */
@JsonDeserialize(using = BuildGemsDeserializer::class)
@JsonSerialize(using = BuildGemsSerializer::class)
class BuildGems(
	weapons: List<Gem>,
	armour: List<Gem>,
	helmet: List<Gem>,
	gloves: List<Gem>,
	boots: List<Gem>,
	updates: List<Update>
) {
	val weaponsProperty = SimpleListProperty<Gem>()
	var weapons by weaponsProperty

	val armourProperty = SimpleListProperty<Gem>()
	var armour by armourProperty

	val helmetProperty = SimpleListProperty<Gem>()
	var helmet by helmetProperty

	val glovesProperty = SimpleListProperty<Gem>()
	var gloves by glovesProperty

	val bootsProperty = SimpleListProperty<Gem>()
	var boots by bootsProperty

	val updatesProperty = SimpleListProperty<Update>()
	var updates by updatesProperty

	init {
		var weaponsTemp = weapons
		while (weaponsTemp.size < Util.WEAPONS_SIZE)
			weaponsTemp = weaponsTemp.plus(Util.MISSING_GEM)
		this.weapons = FXCollections.observableList(weaponsTemp)
		var armourTemp = armour
		while (armourTemp.size < Util.ARMOUR_SIZE)
			armourTemp = armourTemp.plus(Util.MISSING_GEM)
		this.armour = FXCollections.observableList(armourTemp)
		var helmetTemp = helmet
		while (helmetTemp.size < Util.HELMET_SIZE)
			helmetTemp = helmetTemp.plus(Util.MISSING_GEM)
		this.helmet = FXCollections.observableList(helmetTemp)
		var glovesTemp = gloves
		while (glovesTemp.size < Util.GLOVES_SIZE)
			glovesTemp = glovesTemp.plus(Util.MISSING_GEM)
		this.gloves = FXCollections.observableList(glovesTemp)
		var bootsTemp = boots
		while (bootsTemp.size < Util.BOOTS_SIZE)
			bootsTemp = bootsTemp.plus(Util.MISSING_GEM)
		this.boots = FXCollections.observableList(bootsTemp)
		this.updates = FXCollections.observableList(updates)
	}

	override fun toString(): String {
		return "BuildGems(weaponsProperty=$weaponsProperty, armourProperty=$armourProperty, helmetProperty=$helmetProperty, glovesProperty=$glovesProperty, bootsProperty=$bootsProperty, updatesProperty=$updatesProperty)"
	}
}

class BuildGemsDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BuildGems?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGems? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.gemByName(it.asText()) }?.chunked(Util.WEAPONS_SIZE)?.firstOrNull()
			?: emptyList()
		val armour = node["Armour"]?.map { Util.gemByName(it.asText()) }?.chunked(Util.ARMOUR_SIZE)?.firstOrNull()
			?: emptyList()
		val helmet = node["Helmet"]?.map { Util.gemByName(it.asText()) }?.chunked(Util.HELMET_SIZE)?.firstOrNull()
			?: emptyList()
		val gloves = node["Gloves"]?.map { Util.gemByName(it.asText()) }?.chunked(Util.GLOVES_SIZE)?.firstOrNull()
			?: emptyList()
		val boots = node["Boots"]?.map { Util.gemByName(it.asText()) }?.chunked(Util.BOOTS_SIZE)?.firstOrNull()
			?: emptyList()
		val updates = node["Updates"]?.map { Util.YAML_MAPPER.treeToValue(it, Update::class.java) } ?: emptyList()

		return BuildGems(
			weapons = weapons,
			armour = armour,
			helmet = helmet,
			gloves = gloves,
			boots = boots,
			updates = updates
		)
	}
}

class BuildGemsSerializer @JvmOverloads constructor(t: Class<BuildGems>? = null) : StdSerializer<BuildGems>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGems, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons.map { it.getFullname() })
		parser.writeObjectField("Armour", value.armour.map { it.getFullname() })
		parser.writeObjectField("Helmet", value.helmet.map { it.getFullname() })
		parser.writeObjectField("Gloves", value.gloves.map { it.getFullname() })
		parser.writeObjectField("Boots", value.boots.map { it.getFullname() })
		parser.writeObjectField("Updates", value.updates)
		parser.writeEndObject()
	}
}