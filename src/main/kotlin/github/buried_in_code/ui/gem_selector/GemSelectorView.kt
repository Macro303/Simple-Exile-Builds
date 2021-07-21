package github.buried_in_code.ui.gem_selector

import github.buried_in_code.Styles
import github.buried_in_code.Utils
import github.buried_in_code.core.Gem
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import javafx.util.StringConverter
import tornadofx.*

/**
 * Created by Buried-In-Code on 2021-Apr-15.
 */
class GemSelectorView : View("Gem Selector") {
    private val model by inject<GemSelectorModel>()

    private var selectorComboBox: ComboBox<Gem> by singleAssign()

    override val root = borderpane {
        prefWidth = 500.0
        prefHeight = 150.0
        paddingAll = 10.0
        left {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                imageview(model.imageUrlProperty, lazyload = true) {
                    if (model.imageUrl.endsWith("placeholder.png")) {
                        if (Utils.getImageHeight(model.selected) >= Utils.IMG_SIZE)
                            fitHeight = Utils.IMG_SIZE
                        else if (Utils.getImageWidth(model.selected) >= Utils.IMG_SIZE)
                            fitWidth = Utils.IMG_SIZE
                        isPreserveRatio = true
                    }
                }
            }
        }
        center {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                selectorComboBox = combobox(values = Gem.GEM_LIST) {
                    converter = object : StringConverter<Gem>() {
                        override fun toString(item: Gem): String = item.name
                        override fun fromString(string: String): Gem? = null
                    }
                    promptText = "Select Item"
                    hgrow = Priority.ALWAYS
                }
                selectorComboBox.setOnAction {
                    model.select(selectorComboBox.selectedItem)
                }
            }
        }
        bottom {
            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                button("Select") {
                    addClass(Styles.sizedButton)
                    isDefaultButton = true
                    action {
                        model.selected = selectorComboBox.selectedItem
                        close()
                    }
                }
            }
        }
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            model.selected = model.original
        }
    }
}