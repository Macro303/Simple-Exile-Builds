package github.macro.ui.item.gear.helmet

import github.macro.core.item.Items
import github.macro.core.item.gear.helmets.BuildHelmet
import github.macro.core.item.gear.helmets.Helmet
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetSelector : BaseItemSelector<BuildHelmet, Helmet>() {
	init {
		title = "Helmet Selector"
	}

	override fun getSelected(): BuildHelmet? = BuildHelmet(model.selectedItem ?: Items.getHelmetByName(null))
}