package github.macro.core.item.gear.weapons

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.core.item.gear.BaseGear
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-21
 */
@JsonDeserialize(using = WeaponDeserializer::class)
class Weapon(
	id: String,
	name: String,
	isReleased: Boolean,
	isUnique: Boolean,
	val type: WeaponType
) : BaseGear(id = id, name = name, isReleased = isReleased, isUnique = isUnique)

private class WeaponDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Weapon?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Weapon? {
		val node: JsonNode = parser.readValueAsTree()

		val id = node["id"].asText()
		val name = node["name"].asText()
		val isReleased = node["isReleased"]?.asBoolean(false) ?: false
		val isUnique = node["isUnique"]?.asBoolean(false) ?: false
		val type = WeaponType.value(node["type"]?.asText())
		if (type == null) {
			LOGGER.info("Invalid Weapon Type: ${node["type"]?.asText()}")
			return null
		}

		return Weapon(
			id = id,
			name = name,
			isReleased = isReleased,
			isUnique = isUnique,
			type = type
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}