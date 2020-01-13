package github.macro.build_info.gems

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Recipe() : JsonModelAuto {
	val amountProperty = SimpleIntegerProperty()
	var amount by amountProperty

	val ingredientProperty = SimpleStringProperty()
	var ingredient by ingredientProperty

	constructor(amount: Int, ingredient: String) : this() {
		this.amount = amount
		this.ingredient = ingredient
	}

	override fun toString(): String {
		return "Recipe(amountProperty=$amountProperty, ingredientProperty=$ingredientProperty)"
	}
}