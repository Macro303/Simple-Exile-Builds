package github.macro.ui.build_viewer

import github.macro.Utils.cleanName
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
 * Created by Macro303 on 2021-Apr-13.
 */
class BuildViewerModel(private val build: BuildInfo) : ViewModel() {
    val titleProperty = SimpleStringProperty(build.title)
    var title by titleProperty
    val versionProperty = SimpleStringProperty(build.version)
    var version by versionProperty
    val classProperty = SimpleObjectProperty(build.classTag)
    var classTag by classProperty
    val ascendencyProperty = SimpleObjectProperty(build.ascendency)
    var ascendency by ascendencyProperty
    val stageListProperty = SimpleListProperty<Stage>()
    var stageList by stageListProperty
    val banditListProperty = SimpleListProperty<Bandit>()
    var banditList by banditListProperty
    val majorPantheonProperty = SimpleStringProperty(build.majorPantheon.cleanName())
    var majorPantheon by majorPantheonProperty
    val minorPantheonProperty = SimpleStringProperty(build.minorPantheon.cleanName())
    var minorPantheon by minorPantheonProperty
    val detailsProperty = SimpleStringProperty(build.details)
    var details by detailsProperty

    init {
        stageList = FXCollections.observableArrayList(build.stageList)
        banditList = FXCollections.observableArrayList(build.banditList)
    }

    fun copyBuild(): BuildInfo = build.copy(title = build.title + " Copy")

    fun deleteBuild() = build.delete()
}