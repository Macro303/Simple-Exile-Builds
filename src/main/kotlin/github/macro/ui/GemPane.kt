package github.macro.ui

import github.macro.Util
import github.macro.build_info.BuildInfo
import github.macro.build_info.gems.BuildGem
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
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
				val imageFile = File("gems", gem?.getFilename() ?: "INVALID.png")
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
			label(text = gem?.info?.name ?: "Missing Gem Data") {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
			}
		}
		bottom {
			hbox(spacing = 5.0) {
				button(text = "<<") {
					isVisible = build.updates.any { it.newGem.endsWith(gem?.info?.name ?: "INVALID") }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action{
						gem = build.updates.firstOrNull { it.newGem.endsWith(gem?.info?.name ?: "INVALID") }?.oldGem?.let {
							Util.textToGem(it)
						}
						initialize()
					}
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				button(text = ">>") {
					isVisible = build.updates.any { it.oldGem.endsWith(gem?.info?.name ?: "INVALID") }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action{
						gem = build.updates.firstOrNull { it.oldGem.endsWith(gem?.info?.name ?: "INVALID") }?.newGem?.let {
							Util.textToGem(it)
						}
						initialize()
					}
					tooltip(build.updates.firstOrNull { it.oldGem.endsWith(gem?.info?.name ?: "INVALID") }?.reason) {
						style {
							fontSize = 10.pt
						}
					}
				}
			}
		}
	}

	/*public ImageView getGemImage()
	{
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
	}

	public Button getPreviousButton()
	{
		var previousButton = new Button ("<<")
		var previousUpdate = build.getUpdates().stream().filter(update -> {
		var newGem = BuildUtils.getGem(update.getNewGem())
		if (newGem.isEmpty())
			return false
		return newGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem . get ().getFullname() : "Missing Gem")
	}).findFirst()
		previousButton.setVisible(previousUpdate.isPresent())
		if (previousUpdate.isEmpty())
			return previousButton
		var previousGem = BuildUtils.getGem(previousUpdate.get().getOldGem())
		if (previousGem.isEmpty())
			LOGGER.warn("Missing Gem Entry for {}", previousUpdate.get().getOldGem())
		previousButton.setOnAction(event -> {
		if (previousGem.isPresent()) {
			LOGGER.info(
				"Previous Selected, Updating {} to {}",
				gem.isPresent() ? gem . get ().getFullname() : "Missing Gem", previousGem.get().getFullname())
			gem = previousGem
			initialize()
		}
	})
		return previousButton
	}

	public Button getNextButton()
	{
		var nextButton = new Button (">>")
		var nextUpdate = build.getUpdates().stream().filter(update -> {
		var oldGem = BuildUtils.getGem(update.getOldGem())
		if (oldGem.isEmpty())
			return false
		return oldGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem . get ().getFullname() : "Missing Gem")
	}).findFirst()
		nextButton.setVisible(nextUpdate.isPresent())
		if (nextUpdate.isEmpty())
			return nextButton
		var nextGem = BuildUtils.getGem(nextUpdate.get().getNewGem())
		if (nextGem.isEmpty())
			LOGGER.warn("Missing Gem Entry for {}", nextUpdate.get().getNewGem())
		nextButton.setOnAction(event -> {
		if (nextGem.isPresent()) {
			LOGGER.info(
				"Next Selected, Updating {} to {}",
				gem.isPresent() ? gem . get ().getFullname() : "Missing Gem", nextGem.get().getFullname())
			gem = nextGem
			initialize()
		}
	})
		if (nextButton.isVisible()) {
			var tooltip = new Tooltip (nextUpdate.get().getReason())
			tooltip.setShowDelay(Duration.seconds(0))
			tooltip.setStyle("-fx-font-size: 10pt;")
			nextButton.setTooltip(tooltip)
		}
		return nextButton
	}*/
}

/*
class GemPane(val build: BuildInfo, val gem: BuildGem) : GridPane() {

	init {
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
				} else {
					imageview("file:${imageFile.path}") {
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
						Viewer.LOGGER.info("Previous Button Selected")
					}
					isVisible = selected.buildProperty.value.updates.firstOrNull { } != null
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				button(text = ">>") {
					action {
						Viewer.LOGGER.info("Next Button Selected")
					}
				}
			}
		}
	}
}*/
