package github.macro.ui.item.gear.amulet

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.amulet.Amulet
import github.macro.core.item.gear.amulet.BuildAmulet
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletEditor(build: Build) : BaseGearEditor<BuildAmulet, Amulet>(
	build = build,
	buildGear = build.buildGear.amulet,
	index = 0
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.AMULET_LIST,
		selected = build.buildGear.amulet
	)

	override fun addItem(item: BuildAmulet?) {
		item?.reason = null
		build.buildGear.amulet = item ?: BuildAmulet(Items.getAmuletByName("None"))
		assignItem(build.buildGear.amulet)
	}

	override fun getPrevious(): BuildAmulet? {
		var temp = build.buildGear.amulet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildAmulet
		}
		return null
	}

	override fun selectItem(): BuildAmulet {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<AmuletSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildAmulet(Items.getAmuletByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}