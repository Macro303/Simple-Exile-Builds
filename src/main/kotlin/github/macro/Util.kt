package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.ClassTag
import github.macro.build_info.ClassTag.*
import github.macro.build_info.equipment.EquipmentInfo
import github.macro.build_info.gems.Gem
import github.macro.build_info.gems.Slot
import github.macro.build_info.gems.Slot.*
import github.macro.config.Config
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.scene.control.Tooltip
import javafx.util.Duration
import kong.unirest.GenericType
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kong.unirest.UnirestException
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException
import java.lang.reflect.Field

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Util {
    private val LOGGER = LogManager.getLogger(Util::class.java)
    private val HEADERS = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json",
        "User-Agent" to "Exile Buddy"
    )
    internal val JSON_MAPPER: ObjectMapper by lazy {
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.findAndRegisterModules()
        mapper.registerModule(Jdk8Module())
        mapper
    }
    internal val YAML_MAPPER: ObjectMapper by lazy {
        val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.findAndRegisterModules()
        mapper.registerModule(Jdk8Module())
        mapper
    }
    val gems: List<Gem> by lazy {
        try {
            JSON_MAPPER.readValue(File("gems", "Gems.json"), object : TypeReference<List<Gem>>() {})
        } catch (ioe: IOException) {
            LOGGER.error("Unable to Load Gems: $ioe")
            emptyList<Gem>()
        }
    }
    val equipment: List<EquipmentInfo> by lazy {
        try {
            JSON_MAPPER.readValue(File("equipment", "Equipment.json"), object : TypeReference<List<EquipmentInfo>>() {})
        } catch (ioe: IOException) {
            LOGGER.error("Unable to Load Equipment: $ioe")
            emptyList<EquipmentInfo>()
        }
    }

    init {
        Unirest.config().enableCookieManagement(false)
        if (Config.INSTANCE.proxy.hostName != null && Config.INSTANCE.proxy.port != null)
            Unirest.config().proxy(Config.INSTANCE.proxy.hostName, Config.INSTANCE.proxy.port!!)
        Unirest.config().objectMapper = object : kong.unirest.ObjectMapper {
            override fun writeValue(value: Any?): String {
                return JSON_MAPPER.writeValueAsString(value)
            }

            override fun <T : Any?> readValue(value: String?, valueType: Class<T>?): T {
                return JSON_MAPPER.readValue(value, valueType)
            }
        }
    }

    fun slotToColour(slot: Slot?): String = when (slot) {
        RED -> "#DD9999"
        GREEN -> "#99DD99"
        BLUE -> "#9999DD"
        WHITE -> "#DDDDDD"
        else -> "#999999"
    }

    fun gemByName(name: String): Gem? = gems.firstOrNull {
        it.getFullname().equals(name, ignoreCase = true)
    }

    fun equipmentByName(name: String): EquipmentInfo? = equipment.firstOrNull {
        it.name.equals(name, ignoreCase = true)
    }

    internal fun hackTooltipStartTiming(tooltip: Tooltip) {
        try {
            val fieldBehavior: Field = tooltip.javaClass.getDeclaredField("BEHAVIOR")
            fieldBehavior.isAccessible = true
            val objBehavior: Any = fieldBehavior.get(tooltip)
            val fieldTimer: Field = objBehavior.javaClass.getDeclaredField("activationTimer")
            fieldTimer.isAccessible = true
            val objTimer = fieldTimer.get(objBehavior) as Timeline
            objTimer.keyFrames.clear()
            objTimer.keyFrames.add(KeyFrame(Duration(0.0)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun getClassGems(classTag: ClassTag): List<Gem?> = when (classTag) {
        SCION -> listOf("Spectral Throw", "Onslaught Support")
        MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
        RANGER -> listOf("Burning Arrow", "Pierce Support")
        WITCH -> listOf("Fireball", "Arcane Surge Support")
        DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
        TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
        SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
    }.plus("Empower Support").map { gemByName(it) }

    fun <T> httpRequest(url: String, headers: Map<String, String> = HEADERS, klass: GenericType<T>): T? {
        val request = Unirest.get(url)
        request.headers(headers)
        LOGGER.debug("GET : >>> - ${request.url} - $headers")
        val response: HttpResponse<T>
        try {
            response = request.asObject(klass)
        } catch (ue: UnirestException) {
            LOGGER.error("Unable to load URL: $ue")
            return null
        }

        val level = when {
            response.status < 100 -> Level.ERROR
            response.status < 200 -> Level.INFO
            response.status < 300 -> Level.INFO
            response.status < 400 -> Level.WARN
            response.status < 500 -> Level.WARN
            else -> Level.ERROR
        }
        LOGGER.log(level, "GET: ${response.status} ${response.statusText} - ${request.url}")
        LOGGER.debug("Response: ${response.body}")
        return if (response.status != 200) null else response.body
    }
}