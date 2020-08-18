package github.macro.ui.viewer

import github.macro.Util
import github.macro.build_info.gems.Gem
import github.macro.ui.UIModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemViewerPane(val model: UIModel, gem: Gem) : BorderPane() {
	val gemProperty = SimpleObjectProperty<Gem>()
	var gem by gemProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val colourProperty = SimpleObjectProperty<Paint>()
	var colour by colourProperty

	val previousProperty = SimpleBooleanProperty()
	var previous by previousProperty

	val nextProperty = SimpleBooleanProperty()
	var next by nextProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		setNewGem(gem)
		initialize()
	}

	fun setNewGem(newGem: Gem) {
		gemProperty.value = newGem

		style {
			borderColor += box(c(Util.slotToColour(gem.colour)))
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

		var imageFile = gem.getFile()
		if (!imageFile.exists())
			imageFile = File("resources/Gems", "Missing.png")
		imageUrlProperty.value = "file:${imageFile.path}"

		nameProperty.value = gem.getTagname()

		colourProperty.value = Paint.valueOf(Util.slotToColour(gem.colour))

		previousProperty.value = model.selectedBuild.gems.updates.any {
			(it.new == gem) && it.new != Util.MISSING_GEM
		}

		nextProperty.value = model.selectedBuild.gems.updates.any {
			it.old == gem && it.old != Util.MISSING_GEM
		}

		reasonProperty.value = model.selectedBuild.gems.updates.firstOrNull {
			it.old == gem && it.old != Util.MISSING_GEM
		}?.reason
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemViewerPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = 80.0
					fitWidth = 80.0
				}
			}
		}
		center {
			label(nameProperty) {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
				textFillProperty().bind(colourProperty)
			}
		}
		bottom {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					button(text = "<<") {
						visibleWhen(previousProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.new?.equals(gem) ?: false }?.old
									?: Util.MISSING_GEM
							)
						}
					}
					separator {
						isVisible = false
						hgrow = Priority.ALWAYS
					}
					button(text = ">>") {
						visibleWhen(nextProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.old?.equals(gem) ?: false }?.new
									?: Util.MISSING_GEM
							)
						}
						tooltip(reason) {
							style {
								fontSize = 14.px
							}
							textProperty().bind(reasonProperty)
						}
						Util.hackTooltipStartTiming(tooltip)
					}
				}
			}
		}
	}
}
