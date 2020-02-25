package github.macro.config

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
import java.io.IOException

/**
 * Created by Macro303 on 2020-Feb-25.
 */
@JsonDeserialize(using = VersionDeserializer::class)
@JsonSerialize(using = VersionSerializer::class)
data class Version(var major: Int, var minor: Int, var bugfix: Int) : Comparable<Version> {
    constructor(version: String) : this(
        major = version.split('.').getOrNull(0)?.toIntOrNull() ?: 0,
        minor = version.split('.').getOrNull(1)?.toIntOrNull() ?: 0,
        bugfix = version.split('.').getOrNull(2)?.toIntOrNull() ?: 0
    )

    fun toVersion(): String = "$major.$minor.$bugfix"

    override fun compareTo(other: Version): Int =
        compareBy<Version> { major }
            .thenBy { minor }
            .thenBy { bugfix }
            .compare(this, other)
}

class VersionDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Version?>(vc) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Version? {
        val node: JsonNode = parser.codec.readTree(parser)
        return Version(node.asText("0.0.0"))
    }
}

class VersionSerializer @JvmOverloads constructor(t: Class<Version>? = null) : StdSerializer<Version>(t) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Version, gen: JsonGenerator, provider: SerializerProvider?) {
        gen.writeString(value.toVersion())
    }
}