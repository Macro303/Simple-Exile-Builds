package github.macro.ui.item.gear.belt

import github.macro.core.item.Items
import github.macro.core.item.gear.belt.Belt
import github.macro.core.item.gear.belt.BuildBelt
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltSelector : BaseItemSelector<BuildBelt, Belt>() {
	init {
		title = "Belt Selector"
	}

	override fun getSelected(): BuildBelt? = BuildBelt(model.selectedItem ?: Items.getBeltByName(null))
}