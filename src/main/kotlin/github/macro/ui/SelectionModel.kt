package github.macro.ui

import github.macro.core.IBuildItem
import github.macro.core.IItem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class SelectionModel<T : IBuildItem?, S : IItem>(items: List<S>, val imageHeight: Double = 78.0, val imageWidth: Double = 78.0) : ViewModel() {
	val itemsProperty = SimpleListProperty<S>()
	var items by itemsProperty

	val selectedProperty = SimpleObjectProperty<T>()
	var selected by selectedProperty

	init{
		this.items = FXCollections.observableList(items)
	}
}