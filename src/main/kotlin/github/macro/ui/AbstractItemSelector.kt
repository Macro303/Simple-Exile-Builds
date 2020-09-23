package github.macro.ui

import github.macro.Styles
import github.macro.core.IBuildItem
import github.macro.core.IItem
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
abstract class AbstractItemSelector<T : IBuildItem, S : IItem> : View() {
	private val model by inject<SelectionModel<T, S>>()

	private var itemCombobox: ComboBox<S> by singleAssign()
	private val imageUrlProperty = SimpleStringProperty()
	protected var imageUrl by imageUrlProperty
	protected var selectedItem: T? = null

	init {
		model.selected = null
		updateSelection(null)
	}

	protected abstract fun updateSelection(selected: S?)

	override val root = borderpane {
		prefWidth = 500.0
		prefHeight = 150.0
		paddingAll = 10.0
		center {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(imageUrlProperty, lazyload = true) {
					if (!imageUrl.contains("placeholder")) {
						if (model.imageWidth >= 78.0) {
							fitWidth = 78.0
							isPreserveRatio = true
						} else if (model.imageHeight >= 78.0) {
							fitHeight = 78.0
							isPreserveRatio = true
						}
					}
				}
				itemCombobox = combobox<S>(values = model.items.filterNot { it.name == "None" }.sortedBy { it.name }) {
					promptText = "Select Item"
					hgrow = Priority.ALWAYS
					cellFormat {
						text = it.getDisplayName()
					}
					setOnAction {
						updateSelection(itemCombobox.selectedItem)
					}
				}
			}
		}
		bottom {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				button("Select") {
					addClass(Styles.sizedButton)
					isDefaultButton = true
					action {
						model.selected = selectedItem
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
			updateSelection(null)
			model.selected = selectedItem
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}