package github.macro.build_info.gems

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class UpdateGem(
    oldGem: Gem?,
    newGem: Gem?,
    reason: String
) {
    val oldGemProperty = SimpleObjectProperty<Gem?>()
    var oldGem by oldGemProperty

    val newGemProperty = SimpleObjectProperty<Gem?>()
    var newGem by newGemProperty

    val reasonProperty = SimpleStringProperty()
    var reason by reasonProperty

    init {
        this.oldGem = oldGem
        this.newGem = newGem
        this.reason = reason
    }

    override fun toString(): String {
        return "UpdateGem(oldGem=$oldGem, newGem=$newGem, reason=$reason)"
    }
}