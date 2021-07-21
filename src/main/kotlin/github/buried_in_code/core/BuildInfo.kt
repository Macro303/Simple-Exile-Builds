package github.buried_in_code.core

import com.charleskorn.kaml.Yaml
import github.buried_in_code.Utils
import github.buried_in_code.Utils.cleanName
import github.buried_in_code.core.pantheon.MajorPantheon
import github.buried_in_code.core.pantheon.MinorPantheon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Buried-In-Code on 2021-Apr-12.
 */
@Serializable
data class BuildInfo(
    @SerialName("Version")
    var version: String,
    @SerialName("Name")
    var title: String,
    @SerialName("Class")
    var classTag: ClassTag,
    @SerialName("Ascendency")
    var ascendency: Ascendency,
    @SerialName("Gem Slots")
    var gemSlots: GemSlots = GemSlots(),
    @SerialName("Gear Slots")
    var gearSlots: Map<String, String> = emptyMap(),
    @SerialName("Bandits")
    var banditList: List<Bandit>,
    @SerialName("Major Pantheon")
    var majorPantheon: MajorPantheon,
    @SerialName("Minor Pantheon")
    var minorPantheon: MinorPantheon,
    @SerialName("Details")
    var details: String? = null
) {
    val filename: String
        get() = "{$version}${Utils.slugify(title)}.yaml"

    val display: String
        get() = "{$version} $title [${classTag.cleanName()}/${ascendency.cleanName()}]"

    fun save() {
        try {
            val buildFile = File("Builds", filename)
            buildFile.parentFile.mkdirs()
            buildFile.writeText(Yaml.default.encodeToString(this))
        } catch (ioe: IOException) {
            LOGGER.error("Unable to save build: $ioe")
        }
    }

    fun delete() {
        val buildFile = File("Builds", filename)
        try {
            buildFile.delete()
        } catch (ioe: IOException) {
            LOGGER.error("Unable to delete build: $ioe")
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}