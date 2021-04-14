package github.macro.core

import com.charleskorn.kaml.Yaml
import github.macro.Utils
import github.macro.Utils.cleanName
import github.macro.core.pantheon.MajorPantheon
import github.macro.core.pantheon.MinorPantheon
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2021-Apr-12.
 */
@Serializable
data class BuildInfo(
    @SerialName("Version")
    var version: String,
    @SerialName("Name")
    var name: String,
    @SerialName("Class")
    var classTag: ClassTag,
    @SerialName("Ascendency")
    var ascendency: Ascendency,
    @SerialName("Stages")
    val buildStages: List<BuildStage>,
    @SerialName("Bandits")
    val bandits: List<Bandit>,
    @SerialName("Major Pantheon")
    val major: MajorPantheon,
    @SerialName("Minor Pantheon")
    val minor: MinorPantheon,
    @SerialName("Details")
    val details: String? = null
) {

    val filename: String
        get() = "{$version}${Utils.slugify(name)}.yaml"

    val display: String
        get() = "{$version} $name [${classTag.cleanName()}/${ascendency.cleanName()}]"

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