package github.macro.build_info

import github.macro.build_info.gems.BuildGem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class LinkInfo() : JsonModelAuto {
	val groupProperty = SimpleStringProperty()
	var group by groupProperty

	val gemsProperty = SimpleListProperty<BuildGem>()
	var gems by gemsProperty

	constructor(group: String, gems: List<BuildGem>) : this() {
		this.group = group
		this.gems = FXCollections.observableList(gems)
	}

	override fun toString(): String {
		return "LinkInfo(groupProperty=$groupProperty, gemsProperty=$gemsProperty)"
	}
}