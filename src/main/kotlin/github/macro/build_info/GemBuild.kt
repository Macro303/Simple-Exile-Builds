package github.macro.build_info

import github.macro.build_info.gems.Gem
import github.macro.build_info.gems.UpdateGem
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-23.
 */
class GemBuild(
	links: List<List<Gem?>>,
	updates: List<UpdateGem>
) {
	val linksProperty = SimpleListProperty<List<Gem?>>()
	var links by linksProperty

	val updatesProperty = SimpleListProperty<UpdateGem>()
	var updates by updatesProperty

	init {
		this.links = FXCollections.observableList(links)
		this.updates = FXCollections.observableList(updates)
	}

	override fun toString(): String {
		return "GemBuild(links=$links, updates=$updates)"
	}
}