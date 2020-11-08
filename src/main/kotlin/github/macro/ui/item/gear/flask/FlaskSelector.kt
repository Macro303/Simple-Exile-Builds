package github.macro.ui.item.gear.flask

import github.macro.core.item.Items
import github.macro.core.item.gear.flask.BuildFlask
import github.macro.core.item.gear.flask.Flask
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskSelector : BaseItemSelector<BuildFlask, Flask>() {
	init {
		title = "Flask Selector"
	}

	override fun getSelected(): BuildFlask = BuildFlask(model.selectedItem ?: Items.getFlaskByName(null))
}