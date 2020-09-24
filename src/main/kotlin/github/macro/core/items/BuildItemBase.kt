package github.macro.core.items

import github.macro.core.IBuildItem
import github.macro.core.IItem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-24
 */
abstract class BuildItemBase(
	item: ItemBase,
	nextItem: BuildItemBase? = null,
	reason: String? = null,
	preferences: List<String?> = mutableListOf()
) : IBuildItem {
	val itemProperty = SimpleObjectProperty<IItem>()
	final override var item by itemProperty

	val nextItemProperty = SimpleObjectProperty<IBuildItem?>()
	final override var nextItem by nextItemProperty

	val reasonProperty = SimpleStringProperty()
	final override var reason by reasonProperty

	val preferencesProperty = SimpleListProperty<String?>()
	var preferences by preferencesProperty

	init {
		this.item = item
		this.nextItem = nextItem
		this.reason = reason
		this.preferences = FXCollections.observableList(preferences)
	}
}