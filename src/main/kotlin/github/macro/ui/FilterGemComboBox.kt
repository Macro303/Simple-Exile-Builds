package github.macro.ui

import github.macro.build_info.gems.Gem
import javafx.collections.ObservableList
import javafx.scene.control.ComboBox

/**
 * Created by Macro303 on 2020-Aug-18
 */
fun ComboBox<Gem>.makeAutoCompletable() = FilterGemComboBox(this)

class FilterGemComboBox(val comboBox: ComboBox<Gem>) {
	private var data: ObservableList<Gem>? = comboBox.items

	init {
		with(this.comboBox) {
			isEditable = true
			editor.textProperty().addListener { _, _, newValue ->
				val list = data?.filtered { it.getFullname().startsWith(newValue, ignoreCase = true) }

				this.items = list
				if (list?.isEmpty() == false)
					this.show()
			}
		}
	}
}