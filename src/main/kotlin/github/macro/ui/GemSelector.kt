package github.macro.ui

import github.macro.Data
import github.macro.Styles
import github.macro.Util
import github.macro.build_info.gems.Gem
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Aug-18
 */
class GemSelector : View() {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	private var gemCombobox: ComboBox<Gem> by singleAssign()
	private var selectedGem = Data.MISSING_GEM
	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	init {
		model.selectedGem = Data.MISSING_GEM
		updateSelection()
	}

	private fun updateSelection(selected: Gem = Data.MISSING_GEM) {
		selectedGem = selected
		var imageFile = selectedGem.getFile()
		if (!imageFile.exists())
			imageFile = File("resources/Gems", "Missing.png")
		imageUrlProperty.value = "file:${imageFile.path}"
	}

	override val root = borderpane {
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
				paddingAll = 5.0
				label(text = "Exile Buddy") {
					addClass(Styles.title)
				}
			}
		}
		center {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = 80.0
					fitWidth = 80.0
				}
				gemCombobox = combobox<Gem>(values = model.gems.sortedBy { it.name }) {
					promptText = "Select Gem"
					hgrow = Priority.ALWAYS
					cellFormat {
						text = it.getTagname()
					}
					setOnAction {
						updateSelection(gemCombobox.selectedItem ?: Data.MISSING_GEM)
					}
				}/*.makeAutoCompletable()*/
				button("Select") {
					isDefaultButton = true
					action {
						model.selectedGem = selectedGem
						close()
					}
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemSelector::class.java)
	}
}