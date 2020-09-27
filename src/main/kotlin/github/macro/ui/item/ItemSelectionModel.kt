package github.macro.ui.item

import github.macro.core.item.BaseBuildItem
import github.macro.core.item.BaseItem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
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

	val allItemsProperty = SimpleListProperty<S>()
	var allItems by allItemsProperty
	var filteredItems: FilteredList<S>

	val selectedProperty = SimpleObjectProperty<T>()
	var selected by selectedProperty

	val selectedItemProperty = SimpleObjectProperty<S>()
	var selectedItem by selectedItemProperty

	init {
		this.allItems = FXCollections.observableList(items.filterNot { it.name == "None" }
			.sortedBy { it.name.toLowerCase() })
		this.filteredItems = FilteredList<S>(allItems) { true }
		this.selected = selected
		this.selectedItem = selected?.item as S?
		if (selectedItem != null)
			this.imageUrl = "file:${selectedItem.getImageFile().path}"
	}
}