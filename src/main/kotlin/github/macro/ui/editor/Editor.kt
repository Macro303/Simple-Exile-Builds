package github.macro.ui.editor

import github.macro.build_info.BuildInfo
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Editor(val selectedBuild: BuildInfo) : View() {
	override val root = borderpane {
		paddingAll = 10.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = selectedBuild.display())
			}
		}
		center {
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Editor::class.java)
	}
}