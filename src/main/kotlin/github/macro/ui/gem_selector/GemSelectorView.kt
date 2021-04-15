package github.macro.ui.gem_selector

import github.macro.Styles
import github.macro.Utils
import github.macro.core.Ascendency
import github.macro.core.Gem
import javafx.util.StringConverter
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import tornadofx.*

/**
 * Created by Macro303 on 2021-Apr-15.
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
                imageview(
                    "file:${model.viewing.getImageFile().path}",
                    lazyload = true
                ) {
                    if (model.viewing.getImageFile().name != "placeholder.png") {
                        if (Utils.getImageHeight(model.viewing) >= Utils.IMG_SIZE)
                            fitHeight = Utils.IMG_SIZE
                        else if (Utils.getImageWidth(model.viewing) >= Utils.IMG_SIZE)
                            fitWidth = Utils.IMG_SIZE
                        isPreserveRatio = true
                    }
                }
            }
        }
        center {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                selectorComboBox = combobox(values = Gem.GEM_LIST){
                    converter = object : StringConverter<Gem>() {
                        override fun toString(item: Gem): String = item.name
                        override fun fromString(string: String): Gem? = null
                    }
                    promptText = "Select Item"
                    hgrow = Priority.ALWAYS
                    setOnAction {
                        if (this.selectedItem != null) {
                            model.viewing = this.selectedItem
                        }
                    }
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
        }
    }
}