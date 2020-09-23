package github.macro.ui.items.amulets

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.amulets.BuildAmulet
import github.macro.core.items.amulets.ItemAmulet
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletEditorPane(build: Build) : AbstractEditorPane<BuildAmulet, ItemAmulet>(
	build = build,
	buildItem = build.buildItems.amulet,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = SelectionModel<BuildAmulet, ItemAmulet>(
		items = Data.AMULET_LIST
	)

	override fun addItem(item: BuildAmulet?) {
		item?.reason = null
		build.buildItems.amulet = item ?: BuildAmulet(Data.getAmuletByName("None"))
		assignItem(build.buildItems.amulet)
	}

	override fun getPrevious(): BuildAmulet? {
		var temp = build.buildItems.amulet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildAmulet?
		}
		return null
	}

	override fun selectItem(): BuildAmulet {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<AmuletSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildAmulet(Data.getAmuletByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}