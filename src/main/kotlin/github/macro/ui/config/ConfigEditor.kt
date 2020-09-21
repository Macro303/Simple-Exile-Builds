package github.macro.ui.config

import github.macro.Styles
import github.macro.config.Config
import github.macro.ui.BuildSelector
import javafx.geometry.Pos
import javafx.scene.text.FontPosture
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-21
 */
class ConfigEditor : View("Exile Buddy") {

	override val root = borderpane {
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				label(text = "Exile Buddy Config") {
					addClass(Styles.title)
				}
			}
		}
		center {
			paddingAll = 5.0
			gridpane {
				row {
					label(text = "Use Dark Mode") {
						addClass(Styles.subtitle)
					}
					checkbox {
						isSelected = Config.INSTANCE.useDarkMode
						action {
							Config.INSTANCE.useDarkMode = this.isSelected
							Config.INSTANCE.save()
						}
					}
				}
				constraintsForColumn(0).percentWidth = 75.0
				constraintsForColumn(1).percentWidth = 25.0
			}
		}
		bottom {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				label(text = "Requires restart to apply") {
					style {
						fontStyle = FontPosture.ITALIC
					}
				}
			}
		}
	}

	override fun onDock() {
		currentWindow?.setOnCloseRequest {
			find<BuildSelector>().openWindow(owner = null, resizable = false)
			this@ConfigEditor.close()
		}
	}
}