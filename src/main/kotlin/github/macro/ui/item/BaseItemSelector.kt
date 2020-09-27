package github.macro.ui.item

import github.macro.Styles
import github.macro.Util
import github.macro.core.item.BaseBuildItem
import github.macro.core.item.BaseItem
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
abstract class BaseItemSelector<T : BaseBuildItem, S : BaseItem> : View() {
	protected val model by inject<ItemSelectionModel<T, S>>()

	protected abstract fun getSelected(): T?

	override val root = borderpane {
		prefWidth = 500.0
		prefHeight = 150.0
		paddingAll = 10.0
		left {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(model.imageUrlProperty, lazyload = true) {
					if (!model.imageUrl.contains("placeholder")) {
						if (Util.getImageWidth(model.selectedItem) >= 78.0) {
							fitWidth = 78.0
							isPreserveRatio = true
						} else if (Util.getImageHeight(model.selectedItem) >= 78.0) {
							fitHeight = 78.0
							isPreserveRatio = true
						}
					}
				}
			}
		}
		center {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				combobox<S>(
					property = model.selectedItemProperty,
					values = model.filteredItems
				) {
					isEditable = true
					promptText = "Select Item"
					hgrow = Priority.ALWAYS
					converter = object : StringConverter<S?>() {
						override fun toString(build: S?): String = build?.getDisplayName() ?: ""
						override fun fromString(string: String): S? = null
					}
					setOnAction {
						if (model.selectedItem != null)
							model.imageUrl = "file:${model.selectedItem.getImageFile().path}"
					}
					editor.textProperty().addListener { _, _, newValue ->
						Platform.runLater {
							if (selectedItem == null || !selectedItem!!.equals(editor.text))
								model.filteredItems.setPredicate {
									return@setPredicate it.getDisplayName().contains(newValue, ignoreCase = true)
								}
						}
					}
				}
			}
		}
		bottom {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				button("Select") {
					addClass(Styles.sizedButton)
					isDefaultButton = true
					action {
						model.selected = getSelected()
						close()
					}
				}
				button("None") {
					addClass(Styles.sizedButton)
					action {
						model.selected = null
						close()
					}
				}
			}
		}
	}

	override fun onDock() {
		currentWindow?.setOnCloseRequest {
			model.selectedItem = null
			model.selected = getSelected()
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}