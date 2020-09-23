package github.macro.ui.config

import github.macro.Styles
import github.macro.config.Config
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.text.FontPosture
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-21
 */
class ConfigEditor : View("Settings") {

	override val root = borderpane {
		paddingAll = 10.0
		center {
			paddingAll = 5.0
			gridpane {
				row {
					label(text = "Use Dark Mode") {
						addClass(Styles.subtitle)
						gridpaneConstraints {
							margin = Insets(2.5)
						}
					}
					checkbox {
						isSelected = Config.INSTANCE.useDarkMode
						action {
							Config.INSTANCE.useDarkMode = this.isSelected
						}
						gridpaneConstraints {
							margin = Insets(2.5)
						}
					}
				}
				constraintsForColumn(0).percentWidth = 75.0
				constraintsForColumn(1).percentWidth = 25.0
			}
		}
		bottom {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				separator(orientation = Orientation.HORIZONTAL)
				label(text = "Requires restart to apply") {
					style {
						fontStyle = FontPosture.ITALIC
					}
				}
				button(text = "Submit") {
					Config.INSTANCE.save()
				}
			}
		}
	}
}