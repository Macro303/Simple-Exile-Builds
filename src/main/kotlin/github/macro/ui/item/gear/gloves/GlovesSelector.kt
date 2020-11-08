package github.macro.ui.item.gear.gloves

import github.macro.core.item.Items
import github.macro.core.item.gear.gloves.BuildGloves
import github.macro.core.item.gear.gloves.Gloves
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesSelector : BaseItemSelector<BuildGloves, Gloves>() {
	init {
		title = "Gloves Selector"
	}

	override fun getSelected(): BuildGloves = BuildGloves(model.selectedItem ?: Items.getGlovesByName(null))
}