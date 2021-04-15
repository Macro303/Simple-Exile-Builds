package github.macro.ui.gem_selector

import github.macro.core.Gem
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2021-Apr-15.
 */
class GemSelectorModel(private val current: Gem) : ViewModel() {
    val selectedProperty = SimpleObjectProperty(current)
    var selected by selectedProperty

    val viewingProperty = SimpleObjectProperty(current)
    var viewing by viewingProperty
}