package github.macro.build_info.gems

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class UpdateGem() : JsonModelAuto {
	val oldGemProperty = SimpleStringProperty()
	var oldGem by oldGemProperty

	val newGemProperty = SimpleStringProperty()
	var newGem by newGemProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	constructor(oldGem: String, newGem: String, reason: String) : this() {
		this.oldGem = oldGem
		this.newGem = newGem
		this.reason = reason
	}

	override fun toString(): String {
		return "UpdateGem(oldGemProperty=$oldGemProperty, newGemProperty=$newGemProperty, reasonProperty=$reasonProperty)"
	}
}