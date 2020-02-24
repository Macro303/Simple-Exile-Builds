package github.macro.build_info

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import github.macro.Util
import github.macro.build_info.equipment.EquipmentInfo
import github.macro.build_info.gems.GemBuild
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = BuildDeserializer::class)
@JsonSerialize(using = BuildSerializer::class)
class Build(
    name: String,
    classTag: ClassTag,
    ascendency: Ascendency,
    gemBuild: GemBuild,
    equipment: List<EquipmentInfo>
) {
    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val classProperty = SimpleObjectProperty<ClassTag>()
    var classTag by classProperty

    val ascendencyProperty = SimpleObjectProperty<Ascendency>()
    var ascendency by ascendencyProperty

    val gemBuildProperty = SimpleObjectProperty<GemBuild>()
    var gemBuild by gemBuildProperty

    val equipmentProperty = SimpleListProperty<EquipmentInfo>()
    var equipment by equipmentProperty

    init {
        this.name = name
        this.classTag = classTag
        this.ascendency = ascendency
        this.gemBuild = gemBuild
        this.equipment = FXCollections.observableList(equipment)
    }

    override fun toString(): String {
        return "BuildInfo(name=$name, class=$classTag, ascendency=$ascendency, gemBuild=$gemBuild, equipment=$equipment)"
    }

    fun display(): String = "$name [$classTag/$ascendency]"

    fun save() {
        try {
            val buildFile = File("builds", name.replace(" ", "_") + ".yaml")
            Util.YAML_MAPPER.writeValue(buildFile, this)
        } catch (ioe: IOException) {
            LOGGER.error("Unable to save build: $ioe")
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger(Build::class.java)
    }
}