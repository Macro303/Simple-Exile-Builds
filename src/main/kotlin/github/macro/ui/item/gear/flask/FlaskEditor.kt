package github.macro.ui.item.gear.flask

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.flask.BuildFlask
import github.macro.core.item.gear.flask.Flask
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskEditor(build: Build, buildGear: BuildFlask, index: Int) : BaseGearEditor<BuildFlask, Flask>(
	build = build,
	buildGear = buildGear,
	index = index
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.FLASK_LIST,
		selected = build.buildGear.flasks[index]
	)

	override fun addItem(item: BuildFlask?) {
		item?.reason = null
		build.buildGear.flasks[index] = item ?: BuildFlask(Items.getFlaskByName("None"))
		assignItem(build.buildGear.flasks[index])
	}

	override fun getPrevious(): BuildFlask? {
		var temp = build.buildGear.flasks[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildFlask
		}
		return null
	}

	override fun selectItem(): BuildFlask {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<FlaskSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildFlask(Items.getFlaskByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}