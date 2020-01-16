package github.macro.build_info.equipment

import github.macro.Range
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-16.
 */
class EquipmentInfo(
	name: String,
	slot: Slot,
	level: Int,
	damageRange: Range<Range<Number>>,
	damagePerSecond: Range<Number>
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val slotProperty = SimpleObjectProperty<Slot>()
	var slot by slotProperty

	val levelProperty = SimpleIntegerProperty()
	var level by levelProperty

	val damageRangeProperty = SimpleObjectProperty<Range<Range<Number>>>()
	var damageRange by damageRangeProperty

	val damagePerSecondProperty = SimpleObjectProperty<Range<Number>>()
	var damagePerSecond by damagePerSecondProperty

	init {
		this.name = name
		this.slot = slot
		this.level = level
		this.damageRange = damageRange
		this.damagePerSecond = damagePerSecond
	}

	override fun toString(): String {
		return "EquipmentInfo(name=$name, slot=$slot, level=$level, damageRange=$damageRange, damagePerSecond=$damagePerSecond)"
	}
}