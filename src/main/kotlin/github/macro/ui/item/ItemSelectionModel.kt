package github.macro.ui.item

import github.macro.core.item.BaseBuildItem
import github.macro.core.item.BaseItem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
open class ItemSelectionModel<T : BaseBuildItem?, S : BaseItem>(
	items: List<S>,
	selected: T
) : ViewModel() {
	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val itemsProperty = SimpleListProperty<S>()
	var items by itemsProperty

	val selectedProperty = SimpleObjectProperty<T>()
	var selected by selectedProperty

	val selectedItemProperty = SimpleObjectProperty<S>()
	var selectedItem by selectedItemProperty

	init {
		this.items = FXCollections.observableList(items.filterNot { it.name == "None" }
			.sortedBy { it.name.toLowerCase() })
		this.selected = selected
		this.selectedItem = selected?.item as S?
		if (selectedItem != null)
			this.imageUrl = "file:${selectedItem.getImageFile().path}"
	}
}