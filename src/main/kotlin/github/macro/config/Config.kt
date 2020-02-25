package github.macro.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.Util
import kong.unirest.GenericType
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Macro303 on 2020-Feb-25.
 */
class Config {
    val proxy: Connection = Connection()
    var gemVersion: Version = Version("0.0.0")
    var equipmentVersion: Version = Version("0.0.0")

    fun save(): Config {
        Files.newBufferedWriter(Paths.get(filename)).use {
            YAML_MAPPER.writeValue(it, this)
        }
        return this
    }

    companion object {
        private val LOGGER = LogManager.getLogger(Config::class.java)
        private val YAML_MAPPER: ObjectMapper by lazy {
            val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            mapper.findAndRegisterModules()
            mapper.registerModule(Jdk8Module())
            mapper
        }
        private const val filename = "config.yaml"
        val INSTANCE: Config by lazy {
            load()
        }

        fun load(): Config {
            val temp = File(filename)
            if (!temp.exists())
                Config().save()
            return Files.newBufferedReader(Paths.get(filename)).use {
                YAML_MAPPER.readValue(it, Config::class.java)
            }.save()
        }

        fun validateVersions(config: Config = INSTANCE) {
            val githubVersionURL = "https://raw.githubusercontent.com/Macro303/Exile-Buddy/master/versions.json"
            val response = Util.httpRequest(url = githubVersionURL, klass = object : GenericType<Map<String, Version>>() {})
                    ?: return
            LOGGER.info(response)
            if (response["gems"] ?: Version("0.0.0") > config.gemVersion) {
                LOGGER.info("New Gems Version: ${response["gems"]}")
            }
            if (response["equipment"] ?: Version("0.0.0") > config.equipmentVersion) {
                LOGGER.info("New Equipment Version: ${response["equipment"]}")
            }
        }
    }
}