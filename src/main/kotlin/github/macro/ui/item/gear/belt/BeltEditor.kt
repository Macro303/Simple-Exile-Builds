package github.macro.ui.item.gear.belt

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.belt.Belt
import github.macro.core.item.gear.belt.BuildBelt
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltEditor(build: Build) : BaseGearEditor<BuildBelt, Belt>(
	build = build,
	buildGear = build.buildGear.belt,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.BELT_LIST,
		selected = build.buildGear.belt
	)

	override fun addItem(item: BuildBelt?) {
		item?.reason = null
		build.buildGear.belt = item ?: BuildBelt(Items.getBeltByName("None"))
		assignItem(build.buildGear.belt)
	}

	override fun getPrevious(): BuildBelt? {
		var temp = build.buildGear.belt
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBelt
		}
		return null
	}

	override fun selectItem(): BuildBelt {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BeltSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBelt(Items.getBeltByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}