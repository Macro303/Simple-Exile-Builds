package github.macro.build_info.gems

import github.macro.build_info.ClassTag
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Quest() : JsonModelAuto {
	val actProperty = SimpleIntegerProperty()
	var act by actProperty

	val questProperty = SimpleStringProperty()
	var quest by questProperty

	val classesProperty = SimpleObjectProperty<ClassTag>()
	var classes by classesProperty

	constructor(act: Int, quest: String, classes: ClassTag) : this() {
		this.act = act
		this.quest = quest
		this.classes = classes
	}

	override fun toString(): String {
		return "Quest(actProperty=$actProperty, questProperty=$questProperty, classesProperty=$classesProperty)"
	}
}