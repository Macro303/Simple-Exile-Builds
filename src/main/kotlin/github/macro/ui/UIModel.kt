package github.macro.ui

import github.macro.build_info.BuildInfo
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13
 */
class UIModel(val buildProperty : SimpleObjectProperty<BuildInfo>) : ViewModel()