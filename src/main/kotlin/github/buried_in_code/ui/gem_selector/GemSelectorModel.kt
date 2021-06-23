package github.buried_in_code.ui.gem_selector

import github.buried_in_code.Utils
import github.buried_in_code.core.Gem
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2021-Apr-15.
 */
class GemSelectorModel(current: Gem) : ViewModel() {
    val selectedProperty = SimpleObjectProperty<Gem>(current)
    var selected by selectedProperty

    val imageUrlProperty = SimpleStringProperty("file:${current.getImageFile().path}")
    var imageUrl by imageUrlProperty

    val original: Gem = current

    fun select(item: Gem?) {
        val temp = item ?: Utils.getMissingGem()

        this.selected = temp
        this.imageUrl = "file:${temp.getImageFile().path}"
    }
}