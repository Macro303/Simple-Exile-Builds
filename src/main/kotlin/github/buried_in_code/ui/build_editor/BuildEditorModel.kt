package github.buried_in_code.ui.build_editor

import github.buried_in_code.core.Bandit
import github.buried_in_code.core.BuildInfo
import github.buried_in_code.core.GemEntry
import github.buried_in_code.core.GemSlots
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Buried-In-Code on 2021-Apr-14.
 */
class BuildEditorModel(private var build: BuildInfo) : ViewModel() {
    val versionProperty = SimpleStringProperty(build.version)
    var version by versionProperty
    val titleProperty = SimpleStringProperty(build.title)
    var title by titleProperty
    val classProperty = SimpleObjectProperty(build.classTag)
    var classTag by classProperty
    val ascendencyProperty = SimpleObjectProperty(build.ascendency)
    var ascendency by ascendencyProperty
    val gemsWeaponSlotProperty = SimpleListProperty<GemEntry>()
    var gemsWeaponSlot by gemsWeaponSlotProperty
    val gemsHelmetSlotProperty = SimpleListProperty<GemEntry>()
    var gemsHelmetSlot by gemsHelmetSlotProperty
    val gemsBodyArmourSlotProperty = SimpleListProperty<GemEntry>()
    var gemsBodyArmourSlot by gemsBodyArmourSlotProperty
    val gemsGlovesSlotProperty = SimpleListProperty<GemEntry>()
    var gemsGlovesSlot by gemsGlovesSlotProperty
    val gemsBootsSlotProperty = SimpleListProperty<GemEntry>()
    var gemsBootsSlot by gemsBootsSlotProperty
    val banditListProperty = SimpleListProperty<Bandit>()
    var banditList by banditListProperty
    val majorPantheonProperty = SimpleObjectProperty(build.majorPantheon)
    var majorPantheon by majorPantheonProperty
    val minorPantheonProperty = SimpleObjectProperty(build.minorPantheon)
    var minorPantheon by minorPantheonProperty
    val detailsProperty = SimpleStringProperty(build.details)
    var details by detailsProperty

    val originalBuild = build

    init {
        gemsWeaponSlot = FXCollections.observableArrayList(build.gemSlots.weapons)
        gemsHelmetSlot = FXCollections.observableArrayList(build.gemSlots.helmet)
        gemsBodyArmourSlot = FXCollections.observableArrayList(build.gemSlots.bodyArmour)
        gemsGlovesSlot = FXCollections.observableArrayList(build.gemSlots.gloves)
        gemsBootsSlot = FXCollections.observableArrayList(build.gemSlots.boots)
        banditList = FXCollections.observableArrayList(build.banditList)
        banditList = FXCollections.observableArrayList(build.banditList)
    }

    fun saveBuild(): BuildInfo {
        build.delete()

        build.version = version
        build.title = title
        build.classTag = classTag
        build.ascendency = ascendency
        build.gemSlots = GemSlots(
            weapons = gemsWeaponSlot,
            helmet = gemsHelmetSlot,
            bodyArmour = gemsBodyArmourSlot,
            gloves = gemsGlovesSlot,
            boots = gemsBootsSlot
        )
        build.banditList = banditList
        build.majorPantheon = majorPantheon
        build.minorPantheon = minorPantheon
        build.details = details

        build.save()

        return build
    }
}