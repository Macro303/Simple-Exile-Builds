package github.macro.ui.item.gear.boots

import github.macro.core.item.Items
import github.macro.core.item.gear.boots.Boots
import github.macro.core.item.gear.boots.BuildBoots
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsSelector : BaseItemSelector<BuildBoots, Boots>() {
	init {
		title = "Boot Selector"
	}

	override fun getSelected(): BuildBoots = BuildBoots(model.selectedItem ?: Items.getBootsByName(null))
}