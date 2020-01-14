package github.macro.ui.viewer

import github.macro.ui.GemPane
import github.macro.ui.UIModel
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Viewer : View() {
	private val selected: UIModel by inject()

	override val root = borderpane {
		prefWidth = 700.0
		prefHeight = 750.0
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = selected.buildProperty.value.display())
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
			}
		}
		center {
			paddingAll = 5.0
			scrollpane {
				hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
				isFitToWidth = true
				vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
					selected.buildProperty.value.links.forEach { link ->
						hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
							link.forEach { gem ->
								add(GemPane(selected.buildProperty.value, gem))
							}
						}
					}
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Viewer::class.java)
	}
}