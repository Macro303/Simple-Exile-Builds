package github.macro.core.item.gear.gloves

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.core.item.Items
import github.macro.core.item.gear.BaseBuildGear
import github.macro.core.item.gear.BuildGearSerializer
import org.apache.logging.log4j.LogManager
import java.io.IOException

/**
 * Created by Macro303 on 2020-Sep-22
 */
@JsonDeserialize(using = BuildGlovesDeserializer::class)
@JsonSerialize(using = BuildGearSerializer::class)
class BuildGloves(
	gear: Gloves,
	nextGear: BuildGloves? = null,
	reason: String? = null,
	preferences: List<String?> = mutableListOf()
) : BaseBuildGear(gear = gear, nextGear = nextGear, reason = reason, preferences = preferences)

private class BuildGlovesDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
	StdDeserializer<BuildGloves?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGloves {
		val node: JsonNode = parser.codec.readTree(parser)

		val gear = Items.getGlovesById(node["Gear"]?.asText())
		val nextGear = if (node["Next Gear"]?.isNull != false)
			null
		else
			Util.YAML_MAPPER.treeToValue(node["Next Gear"], BuildGloves::class.java)
		val reason = if (node["Reason"]?.isNull != false) null else node["Reason"].asText()
		val preferences = node["Preferences"]?.map {
			if (it?.isNull != false)
				null
			else
				it.asText()
		}?.chunked(3)?.firstOrNull() ?: mutableListOf()

		return BuildGloves(
			gear = gear,
			nextGear = nextGear,
			reason = reason,
			preferences = preferences
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}