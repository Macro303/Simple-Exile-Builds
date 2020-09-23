package github.macro.ui.items.boots

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.boots.BuildBoots
import github.macro.core.items.boots.ItemBoots
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsEditorPane(build: Build) : AbstractEditorPane<BuildBoots, ItemBoots>(
	build = build,
	buildItem = build.buildItems.boots,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = SelectionModel<BuildBoots, ItemBoots>(Data.BOOTS_LIST)

	override fun addItem(item: BuildBoots?) {
		item?.reason = null
		build.buildItems.boots = item ?: BuildBoots(Data.getBoots("None"))
		assignItem(build.buildItems.boots)
	}

	override fun getPrevious(): BuildBoots? {
		var temp = build.buildItems.boots
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBoots?
		}
		return null
	}

	override fun selectItem(): BuildBoots {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BootsSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBoots(Data.getBoots("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}