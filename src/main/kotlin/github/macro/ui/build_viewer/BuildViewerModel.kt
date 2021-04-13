package github.macro.ui.build_viewer

import github.macro.core.BuildInfo
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * Created by Macro303 on 2021-Apr-13.
 */
class BuildViewerModel(private val build: BuildInfo) : ViewModel() {
    val titleProperty = SimpleStringProperty(build.name)
    val versionProperty = SimpleStringProperty(build.version)
    val classProperty = SimpleObjectProperty(build.classTag)
    val ascendencyProperty = SimpleObjectProperty(build.ascendency)

    val detailsProperty = SimpleStringProperty(build.details)

    val stages = build.buildStages
}