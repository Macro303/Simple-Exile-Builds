package github.macro.ui.item.gem

import github.macro.core.item.Items
import github.macro.core.item.gem.BuildGem
import github.macro.core.item.gem.Gem
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Aug-18
 */
class GemSelector : BaseItemSelector<BuildGem, Gem>() {
	init {
		title = "Gem Selector"
	}

	override fun getSelected(): BuildGem? = BuildGem(model.selectedItem ?: Items.getGemByName(null))
}