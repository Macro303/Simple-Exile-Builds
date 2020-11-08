package github.macro.ui.item

import github.macro.Styles
import github.macro.Util
import github.macro.core.build_info.Build
import github.macro.core.item.BaseBuildItem
import github.macro.core.item.BaseItem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-21
 */
abstract class BaseItemEditor<T : BaseBuildItem, S : BaseItem>(
	protected val build: Build,
	buildItem: T,
	val index: Int,
	protected val columnCount: Int = 6
) : BorderPane() {
	val buildItemProperty = SimpleObjectProperty<T>()
	var buildItem by buildItemProperty

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

	protected abstract val selectionModel: ItemSelectionModel<T, S>

	init {
		maxWidth = (Util.UI_PREF_WIDTH - (10 * columnCount)) / columnCount
		prefWidth = (Util.UI_PREF_WIDTH - (20 * columnCount)) / columnCount
		minWidth = (Util.UI_PREF_WIDTH - (30 * columnCount)) / columnCount
		assignItem(buildItem)
		initialize()
	}

	protected open fun assignItem(newItem: T) {
		buildItem = newItem

		style {
			borderColor += box(c(buildItem.item.colour.hex))
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

		imageUrl = "file:${buildItem.item.getImageFile().path}"

		name = buildItem.item.getDisplayName()
		colour = Paint.valueOf(buildItem.item.colour.hex)

		previous = getPrevious() != null
		next = getNext() != null
		reason = buildItem.nextItem?.reason
	}

	protected abstract fun getPrevious(): T?
	protected open fun getNext(): T? {
		return buildItem.nextItem as T?
	}

	protected abstract fun selectItem(): T
	protected abstract fun addItem(item: T?)

	protected open fun initialize() {
		paddingAll = 5.0
		center {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(imageUrlProperty, lazyload = true) {
					if (!imageUrl.contains("placeholder")) {
						if (Util.getImageHeight(buildItem.item) >= Util.IMG_SIZE) {
							fitHeight = Util.IMG_SIZE
							isPreserveRatio = true
						} else if (Util.getImageWidth(buildItem.item) >= Util.IMG_SIZE) {
							fitWidth = Util.IMG_SIZE
							isPreserveRatio = true
						}
					}
				}
			}
		}
		left {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				button(text = "⬅") {
					visibleWhen(previousProperty)
					isFocusTraversable = false
					action {
						assignItem(getPrevious()!!)
					}
				}
			}
		}
		right {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				button(text = "➡") {
					visibleWhen(nextProperty)
					isFocusTraversable = false
					action {
						assignItem(getNext()!!)
					}
					tooltip(reason) {
						showDelay = Duration(0.0)
						hideDelay = Duration(0.0)
						addClass(Styles.tooltip)
						textProperty().bind(reasonProperty)
					}
				}
			}
		}
		bottom {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				label(nameProperty) {
					isWrapText = true
					alignment = Pos.CENTER
					textAlignment = TextAlignment.CENTER
					textFillProperty().bind(colourProperty)
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					button("➕") {
						useMaxWidth = true
						hiddenWhen((nextProperty.or(buildItem.item.name == "Missing")))
						action {
							val selected = selectItem()
							if (selected.item.name != "Missing") {
								selected.reason = "Reason not Implemented"
								buildItem.nextItem = selected
								assignItem(selected)
							}
						}
					}
					button("✏") {
						useMaxWidth = true
						action {
							val selected = selectItem()
							if (selected.item.name != "Missing") {
								val previous = getPrevious()
								if (previous != null)
									previous.nextItem = selected
								else
									addItem(selected)
								selected.nextItem = buildItem.nextItem
								selected.reason = buildItem.reason
								assignItem(selected)
							}
						}
					}
					button("✖") {
						useMaxWidth = true
						visibleWhen(previousProperty.or(nextProperty))
						action {
							val previous = getPrevious()
							if (buildItem.nextItem != null) {
								if (previous != null) {
									previous.nextItem = buildItem.nextItem
									assignItem(buildItem.nextItem as T)
								} else
									addItem(buildItem.nextItem as T)
							} else if (previous != null) {
								previous.nextItem = null
								assignItem(previous)
							} else
								addItem(null)
						}
					}
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}