package github.macro.core

import com.charleskorn.kaml.Yaml
import github.macro.Utils
import github.macro.Utils.cleanName
import github.macro.core.pantheon.MajorPantheon
import github.macro.core.pantheon.MinorPantheon
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
    var version: String,
    var name: String,
    var classTag: ClassTag,
    var ascendency: Ascendency,
    val buildStages: List<BuildStage>,
    val bandits: List<Bandit>,
    val major: MajorPantheon,
    val minor: MinorPantheon,
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