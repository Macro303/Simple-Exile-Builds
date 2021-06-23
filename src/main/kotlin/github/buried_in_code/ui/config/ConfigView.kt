package github.buried_in_code.ui.config

import github.buried_in_code.Styles
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.text.FontPosture
import tornadofx.*

/**
 * Created by Macro303 on 2021-Apr-14.
 */
class ConfigView : View("Settings") {
    private val model by inject<ConfigModel>()

    override val root = borderpane {
        paddingAll = 10.0
        center {
            paddingAll = 5.0
            vbox(spacing = 5.0, alignment = Pos.TOP_LEFT) {
                paddingAll = 5.0
                checkbox("Use Dark Mode", property = model.darkModeProperty) {
                    addClass(Styles.subtitle)
                    isDisable = true
                }
                checkbox("Settings #2") {
                    addClass(Styles.subtitle)
                    isDisable = true
                }
                checkbox("Settings #3") {
                    addClass(Styles.subtitle)
                    isDisable = true
                }
                spacer { }
            }
        }
        bottom {
            paddingAll = 5.0
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                separator(orientation = Orientation.HORIZONTAL)
                label(text = "Requires restart to apply") {
                    style {
                        fontStyle = FontPosture.ITALIC
                    }
                }
                button(text = "Submit") {
                    action {
                        model.save()
                        close()
                    }
                }
            }
        }
    }
}