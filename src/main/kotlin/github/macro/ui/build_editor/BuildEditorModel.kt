package github.macro.ui.build_editor

import github.macro.core.Bandit
import github.macro.core.BuildInfo
import github.macro.core.Stage
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2021-Apr-14.
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
    val stageListProperty = SimpleListProperty<Stage>()
    var stageList by stageListProperty
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
        stageList = FXCollections.observableArrayList(build.stageList)
        banditList = FXCollections.observableArrayList(build.banditList)
    }

    fun saveBuild(): BuildInfo {
        build.delete()

        build.version = version
        build.title = title
        build.classTag = classTag
        build.ascendency = ascendency
        build.stageList = stageList
        build.banditList = banditList
        build.majorPantheon = majorPantheon
        build.minorPantheon = minorPantheon
        build.details = details

        build.save()

        return build
    }
}