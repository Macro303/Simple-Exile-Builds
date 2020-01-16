package github.macro.ui

import github.macro.Util
import github.macro.build_info.BuildInfo
import github.macro.build_info.gems.BuildGem
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File


/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemPane(val build: BuildInfo, var gem: BuildGem?) : BorderPane() {

	init {
		initialize()
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		style(append = true) {
			borderColor += box(c(Util.slotToColour(gem?.info?.slot ?: "Black")))
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

		top {
			hbox {
				val imageFile = File("gems", gem?.getFilename() ?: "Missing Gem")
				if (imageFile.exists())
					imageview("file:${imageFile.path}") {
						fitHeight = 80.0
						fitWidth = 80.0
						alignment = Pos.CENTER
					}
				else
					imageview(GemPane::class.java.getResource("placeholder[80x80].png").toExternalForm()) {
						fitHeight = 80.0
						fitWidth = 80.0
						alignment = Pos.CENTER
					}
			}
		}
		center {
			label(text = gem?.info?.name ?: "Missing Gem") {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
			}
		}
		bottom {
			hbox(spacing = 5.0) {
				button(text = "<<") {
					isVisible = build.updates.any { it.newGem.endsWith(gem?.info?.name ?: "Missing Gem") }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action {
						val oldGem = gem
						gem = build.updates.firstOrNull { it.newGem.endsWith(gem?.info?.name ?: "Missing Gem") }
							?.oldGem?.let {
							Util.gemByName(it)
						}
						LOGGER.info("Previous Selected, Updated ${oldGem?.getFullname() ?: "Missing Gem"} to ${gem?.getFullname() ?: "Missing Gem"}")
						initialize()
					}
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				button(text = ">>") {
					isVisible = build.updates.any { it.oldGem.endsWith(gem?.info?.name ?: "Missing Gem") }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action {
						val oldGem = gem
						gem = build.updates.firstOrNull { it.oldGem.endsWith(gem?.info?.name ?: "Missing Gem") }
							?.newGem?.let {
							Util.gemByName(it)
						}
						LOGGER.info("Next Selected, Updated ${oldGem?.getFullname() ?: "Missing Gem"} to ${gem?.getFullname() ?: "Missing Gem"}")
						initialize()
					}
					tooltip(build.updates.firstOrNull { it.oldGem.endsWith(gem?.info?.name ?: "Missing Gem") }?.reason) {
						style {
							fontSize = 10.pt
						}
					}
					Util.hackTooltipStartTiming(tooltip)
				}
			}
		}
	}

	/*public ImageView getGemImage(){
		var imageFile = getClass().getResource("placeholder[80x80].png").toExternalForm()
		if (gem.isPresent()) {
			var temp = String.format("gems\\%s", gem.get().getFilename())
			if (new File (temp).exists())
				imageFile = "file:" + temp
		}
		var image = new ImageView (new Image (imageFile))
		image.setFitHeight(80)
		image.setFitWidth(80)
		if (gem.isPresent()) {
			var display = gem.get().getInfo().getAcquisition().getDisplay(build.getClassTag())
			if (display.length() > 2) {
				var tooltip = new Tooltip (display)
				tooltip.setShowDelay(Duration.seconds(0))
				tooltip.setStyle("-fx-font-size: 10pt;")
				Tooltip.install(image, tooltip)
			}
		}
		return image
	}*/
}
