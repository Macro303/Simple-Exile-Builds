package github.macro.ui

import github.macro.core.BuildInfo
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

/**
 * Created by Macro303 on 2021-Apr-13.
 */
class BuildModel(private val build: BuildInfo) : ViewModel(){
    val titleProperty = SimpleStringProperty(build.name)
}