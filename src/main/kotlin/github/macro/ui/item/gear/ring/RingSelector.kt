package github.macro.ui.item.gear.ring

import github.macro.core.item.Items
import github.macro.core.item.gear.rings.BuildRing
import github.macro.core.item.gear.rings.Ring
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingSelector : BaseItemSelector<BuildRing, Ring>() {
	init {
		title = "Ring Selector"
	}

	override fun getSelected(): BuildRing? = BuildRing(model.selectedItem ?: Items.getRingByName(null))
}