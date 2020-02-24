package github.macro.ui

import github.macro.build_info.Build
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13
 */
class UIModel(build: Build?) : ViewModel() {
    val buildProperty = SimpleObjectProperty<Build?>()
    var build by buildProperty

    init {
        this.build = build
    }
}