package github.macro.ui.item.gear.boots

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.boots.Boots
import github.macro.core.item.gear.boots.BuildBoots
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsEditor(build: Build) : BaseGearEditor<BuildBoots, Boots>(
	build = build,
	buildGear = build.buildGear.boots,
	index = 0
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.BOOTS_LIST,
		selected = build.buildGear.boots
	)

	override fun addItem(item: BuildBoots?) {
		item?.reason = null
		build.buildGear.boots = item ?: BuildBoots(Items.getBootsByName("None"))
		assignItem(build.buildGear.boots)
	}

	override fun getPrevious(): BuildBoots? {
		var temp = build.buildGear.boots
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBoots
		}
		return null
	}

	override fun selectItem(): BuildBoots {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BootsSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBoots(Items.getBootsByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}