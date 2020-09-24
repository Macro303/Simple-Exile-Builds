package github.macro.ui

import github.macro.Styles
import github.macro.Util
import github.macro.core.IBuildItem
import github.macro.core.build_info.Build
import github.macro.core.gems.Colour
import github.macro.core.items.BuildItemBase
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-21
 */
abstract class AbstractViewerPane<T : IBuildItem>(
	protected val build: Build,
	buildItem: T,
	val index: Int,
	protected val imageHeight: Double = 78.0,
	protected val imageWidth: Double = 78.0,
	protected val columnCount: Int = 6
) : BorderPane() {
	val buildItemProperty = SimpleObjectProperty<T>()
	var buildItem by buildItemProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty
	val rewardsProperty = SimpleStringProperty()
	var rewards by rewardsProperty
	val colourProperty = SimpleObjectProperty<Paint>()
	var colour by colourProperty

	val previousProperty = SimpleBooleanProperty()
	var previous by previousProperty
	val nextProperty = SimpleBooleanProperty()
	var next by nextProperty
	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		assignItem(buildItem)
		initialize()
	}

	protected open fun assignItem(newItem: T) {
		buildItem = newItem

		style {
			borderColor += box(c(Util.colourToHex(Colour.WHITE)))
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

		var image = File(File("resources", "Images"), buildItem.item.id.substringAfterLast("/") + ".png")
		if (buildItem.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"

		name = buildItem.item.getDisplayName()
		rewards = "Not available as a reward"
		colour = Paint.valueOf(Util.colourToHex(Colour.WHITE))

		previous = getPrevious() != null
		next = getNext() != null
		reason = buildItem.nextItem?.reason
	}

	protected abstract fun getPrevious(): T?
	protected open fun getNext(): T? {
		return buildItem.nextItem as T?
	}

	protected fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				imageview(imageUrlProperty, lazyload = true) {
					if (!imageUrl.contains("placeholder")) {
						if (imageWidth >= 78.0) {
							fitWidth = 78.0
							isPreserveRatio = true
						} else if (imageHeight >= 78.0) {
							fitHeight = 78.0
							isPreserveRatio = true
						}
					}
				}
			}
		}
		center {
			label(nameProperty) {
				isWrapText = true
				prefWidth = (Util.UI_PREF_WIDTH - (10 * columnCount)) / columnCount
				prefHeight = if (columnCount >= 6) 75.0 else 30.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
				textFillProperty().bind(colourProperty)
				tooltip(rewards) {
					addClass(Styles.tooltip)
					textProperty().bind(rewardsProperty)
				}
				Util.hackTooltipStartTiming(tooltip)
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
							assignItem(getPrevious()!!)
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
							assignItem(getNext()!!)
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
				if (buildItem is BuildItemBase) {
					if ((buildItem as BuildItemBase).preferences.size >= 1 && (buildItem as BuildItemBase).preferences[0] != null) {
						row(title = "1") {
							label((buildItem as BuildItemBase).preferences[0]!!) {
								gridpaneConstraints {
									columnSpan = 3
									margin = Insets(2.5)
								}
							}
						}
						if ((buildItem as BuildItemBase).preferences.size >= 2 && (buildItem as BuildItemBase).preferences[1] != null) {
							row(title = "2") {
								label((buildItem as BuildItemBase).preferences[1]!!) {
									gridpaneConstraints {
										columnSpan = 3
										margin = Insets(2.5)
									}
								}
							}
							if ((buildItem as BuildItemBase).preferences.size >= 3 && (buildItem as BuildItemBase).preferences[2] != null) {
								row(title = "3") {
									label((buildItem as BuildItemBase).preferences[2]!!) {
										gridpaneConstraints {
											columnSpan = 3
											margin = Insets(2.5)
										}
									}
								}
							}
						}
					}
				}
				constraintsForColumn(0).percentWidth = 100.0 / 3.0
				constraintsForColumn(1).percentWidth = 100.0 / 3.0
				constraintsForColumn(2).percentWidth = 100.0 / 3.0
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}