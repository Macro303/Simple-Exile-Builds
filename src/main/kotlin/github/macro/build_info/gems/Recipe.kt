package github.macro.build_info.gems

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Recipe(
    amount: Int,
    ingredient: String
) {
    val amountProperty = SimpleIntegerProperty()
    var amount by amountProperty

    val ingredientProperty = SimpleStringProperty()
    var ingredient by ingredientProperty

    init {
        this.amount = amount
        this.ingredient = ingredient
    }

    override fun toString(): String {
        return "Recipe(amount=$amount, ingredient=$ingredient)"
    }
}