package github.macro.build_info.gems

import github.macro.build_info.ClassTag
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Quest(
    act: Int,
    quest: String,
    classes: List<ClassTag>
) {
    val actProperty = SimpleIntegerProperty()
    var act by actProperty

    val questProperty = SimpleStringProperty()
    var quest by questProperty

    val classesProperty = SimpleListProperty<ClassTag>()
    var classes by classesProperty

    init {
        this.act = act
        this.quest = quest
        this.classes = FXCollections.observableList(classes)
    }

    override fun toString(): String {
        return "Quest(act=$act, quest=$quest, classes=$classes)"
    }
}