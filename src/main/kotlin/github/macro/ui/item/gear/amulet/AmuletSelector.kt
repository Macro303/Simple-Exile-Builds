package github.macro.ui.item.gear.amulet

import github.macro.core.item.Items
import github.macro.core.item.gear.amulet.Amulet
import github.macro.core.item.gear.amulet.BuildAmulet
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletSelector : BaseItemSelector<BuildAmulet, Amulet>() {
	init {
		title = "Amulet Selector"
	}

	override fun getSelected(): BuildAmulet? = BuildAmulet(model.selectedItem ?: Items.getAmuletByName(null))
}