package github.macro.ui.viewer

import github.macro.Util
import github.macro.ui.UIModel
import javafx.geometry.Pos
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
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = selected.buildProperty.value.display())
			}
		}
		center {
			paddingAll = 5.0
			scrollpane {
				vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
					selected.buildProperty.value.links.forEach { link ->
						hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
							link.forEach { gem ->
								gridpane {
									paddingAll = 5.0
									style(append = true) {
										borderColor += box(c(Util.slotToColour(gem.info?.slot ?: "Black")))
										borderStyle += BorderStrokeStyle(
											StrokeType.CENTERED,
											StrokeLineJoin.ROUND,
											StrokeLineCap.ROUND,
											0.0,
											0.0,
											listOf(5.0, 5.0)
										)
										borderWidth += box(2.px)
									}
									row {
										val imageFile = File("gems", gem.getFilename())
										if (!imageFile.exists()) {
											imageview(Viewer::class.java.getResource("placeholder[80x80].png").toExternalForm()) {
												fitHeight = 80.0
												fitWidth = 80.0
												alignment = Pos.CENTER
												gridpaneConstraints {
													columnSpan = 3
												}
											}
										}else{
											imageview("file:${imageFile.path}"){
												fitHeight = 80.0
												fitWidth = 80.0
												alignment = Pos.CENTER
												gridpaneConstraints {
													columnSpan = 3
												}
											}
										}
									}
									row {
										label(text = gem.info?.name ?: "Missing Gem Data") {
											isWrapText = true
											prefWidth = 90.0
											gridpaneConstraints {
												columnSpan = 3
											}
										}
									}
									row {
										button(text = "<<") {
											action {
												LOGGER.info("Previous Button Selected")
											}
										}
										separator {
											isVisible = false
											hgrow = Priority.ALWAYS
										}
										button(text = ">>") {
											action {
												LOGGER.info("Next Button Selected")
											}
										}
									}
								}
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