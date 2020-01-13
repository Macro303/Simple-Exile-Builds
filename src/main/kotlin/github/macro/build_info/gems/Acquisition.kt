package github.macro.build_info.gems

import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Acquisition() : JsonModelAuto {
	val recipesProperty = SimpleListProperty<Recipe>()
	var recipes by recipesProperty

	val questsProperty = SimpleListProperty<Quest>()
	var quests by questsProperty

	val vendorsProperty = SimpleListProperty<Vendor>()
	var vendors by vendorsProperty

	constructor(recipes: List<Recipe>, quests: List<Quest>, vendors: List<Vendor>) : this() {
		this.recipes = FXCollections.observableList(recipes)
		this.quests = FXCollections.observableList(quests)
		this.vendors = FXCollections.observableList(vendors)
	}

	override fun toString(): String {
		return "Acquisition(recipesProperty=$recipesProperty, questsProperty=$questsProperty, vendorsProperty=$vendorsProperty)"
	}
}