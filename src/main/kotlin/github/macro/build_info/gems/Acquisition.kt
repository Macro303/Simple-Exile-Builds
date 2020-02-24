package github.macro.build_info.gems

import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Acquisition(
    recipes: List<Recipe>,
    quests: List<Quest>,
    vendors: List<Vendor>
) {
    val recipesProperty = SimpleListProperty<Recipe>()
    var recipes by recipesProperty

    val questsProperty = SimpleListProperty<Quest>()
    var quests by questsProperty

    val vendorsProperty = SimpleListProperty<Vendor>()
    var vendors by vendorsProperty

    init {
        this.recipes = FXCollections.observableList(recipes)
        this.quests = FXCollections.observableList(quests)
        this.vendors = FXCollections.observableList(vendors)
    }

    override fun toString(): String {
        return "Acquisition(recipes=$recipes, quests=$quests, vendors=$vendors)"
    }
}