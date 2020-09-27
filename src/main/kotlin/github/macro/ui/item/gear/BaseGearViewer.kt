package github.macro.ui.item.gear

import github.macro.Styles
import github.macro.Util
import github.macro.core.build_info.Build
import github.macro.core.item.gear.BaseBuildGear
import github.macro.ui.item.BaseItemViewer
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-25
 */
abstract class BaseGearViewer<T : BaseBuildGear>(
	build: Build,
	buildGear: T,
	index: Int
) : BaseItemViewer<T>(build = build, buildItem = buildGear, index = index, columnCount = 5) {

	override fun initialize() {
		paddingAll = 5.0
		center {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = Util.IMG_SIZE
					fitWidth = Util.IMG_SIZE
					isPreserveRatio = true
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
				gridpane {
					row {
						label("1") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
						label(buildItem.preferences.getOrNull(0) ?: "") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
					}
					row {
						label("2") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
						label(buildItem.preferences.getOrNull(1) ?: "") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
					}
					row {
						label("3") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
						label(buildItem.preferences.getOrNull(2) ?: "") {
							textFillProperty().bind(colourProperty)
							gridpaneConstraints {
								margin = Insets(2.5)
							}
						}
					}
					constraintsForColumn(0).percentWidth = 10.0
					constraintsForColumn(1).percentWidth = 90.0
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}