package github.macro.ui.rings

import github.macro.Styles
import github.macro.Util
import github.macro.build_info.rings.Ring
import github.macro.ui.UIModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
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
class RingViewerPane(val model: UIModel, ring: Ring) : BorderPane() {
	val ringProperty = SimpleObjectProperty<Ring>()
	var ring by ringProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val previousProperty = SimpleBooleanProperty()
	var previous by previousProperty

	val nextProperty = SimpleBooleanProperty()
	var next by nextProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		assignRing(ring)
		initialize()
	}

	fun assignRing(newRing: Ring) {
		ring = newRing

		style {
			borderColor += box(c(Util.colourToHex(null)))
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

		var imageFile = ring.getFile()
		if (!imageFile.exists())
			imageFile = File(File("resources", "Rings"), "placeholder.png")
		imageUrl = "file:${imageFile.path}"

		name = ring.name

		previous = false
		next = false
		reason = null

		/*previous = model.selectedBuild.gems.updates.any {
			(it.new == gem) && it.new != Data.MISSING_GEM
		}

		next = model.selectedBuild.gems.updates.any {
			it.old == gem && it.old != Data.MISSING_GEM
		}

		reason = model.selectedBuild.gems.updates.firstOrNull {
			it.old == gem && it.old != Data.MISSING_GEM
		}?.reason*/
	}

	companion object {
		private val LOGGER = LogManager.getLogger(RingViewerPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = 78.0
					fitWidth = 78.0
				}
			}
		}
		center {
			label(nameProperty) {
				isWrapText = true
				prefWidth = (Util.UI_PREF_WIDTH - 50) / 5
				prefHeight = 30.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
			}
		}
		bottom {
			gridpane {
				row {
					button(text = "⬅") {
						useMaxWidth = true
						visibleWhen(previousProperty)
						isFocusTraversable = false
						action {
							LOGGER.info("Selected Previous Ring")
//							assignRing(//TODO)
						}
						gridpaneConstraints {
							margin = Insets(2.5)
						}
					}
					separator {
						isVisible = false
						gridpaneConstraints {
							margin = Insets(2.5)
						}
					}
					button(text = "➡") {
						useMaxWidth = true
						visibleWhen(nextProperty)
						isFocusTraversable = false
						action {
							LOGGER.info("Selected Next Ring")
//							assignRing(//TODO)
						}
						tooltip(reason) {
							addClass(Styles.tooltip)
							textProperty().bind(reasonProperty)
						}
						Util.hackTooltipStartTiming(tooltip)
						gridpaneConstraints {
							margin = Insets(2.5)
						}
					}
				}
				constraintsForColumn(0).percentWidth = 100.0/3.0
				constraintsForColumn(1).percentWidth = 100.0/3.0
				constraintsForColumn(2).percentWidth = 100.0/3.0
			}
		}
	}
}
