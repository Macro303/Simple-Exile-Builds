package github.macro.core.items

import github.macro.core.IItem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-16.
 */
abstract class ItemBase(
	id: String,
	name: String,
	isReleased: Boolean,
	isUnique: Boolean,
	level: Int?,
	quality: Double?
) : IItem {
	val idProperty = SimpleStringProperty()
	override var id by idProperty

	val nameProperty = SimpleStringProperty()
	override var name by nameProperty

	val isReleasedProperty = SimpleBooleanProperty()
	override var isReleased by isReleasedProperty

	val isUniqueProperty = SimpleBooleanProperty()
	var isUnique by isUniqueProperty

	val levelProperty = SimpleObjectProperty<Int?>()
	var level by levelProperty

	val qualityProperty = SimpleObjectProperty<Double?>()
	var quality by qualityProperty

	init {
		this.id = id
		this.name = name
		this.isReleased = isReleased
		this.isUnique = isUnique
		this.level = level
		this.quality = quality
	}
}