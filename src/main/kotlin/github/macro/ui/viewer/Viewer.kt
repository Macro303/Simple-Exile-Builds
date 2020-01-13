package github.macro.ui.viewer

import github.macro.Util
import github.macro.ui.GemPane
import github.macro.ui.UIModel
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Viewer : View() {
	private val selected: UIModel by inject()

	override val root = borderpane {
		prefWidth = 800.0
		prefHeight = 500.0
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