package github.macro.ui.item.gear.body_armour

import github.macro.core.item.Items
import github.macro.core.item.gear.body_armour.BodyArmour
import github.macro.core.item.gear.body_armour.BuildBodyArmour
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourSelector : BaseItemSelector<BuildBodyArmour, BodyArmour>() {
	init {
		title = "Body Armour Selector"
	}

	override fun getSelected(): BuildBodyArmour = BuildBodyArmour(
		model.selectedItem ?: Items.getBodyArmourByName(null)
	)
}