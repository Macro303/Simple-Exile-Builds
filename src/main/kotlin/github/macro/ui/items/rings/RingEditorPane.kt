package github.macro.ui.items.rings

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.rings.BuildRing
import github.macro.core.items.rings.ItemRing
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingEditorPane(build: Build, buildItem: BuildRing, index: Int) : AbstractEditorPane<BuildRing, ItemRing>(
	build = build,
	buildItem = buildItem,
	index = index,
	columnCount = 2
) {
	override val selectionModel = SelectionModel<BuildRing, ItemRing>(Data.RING_LIST)

	override fun addItem(item: BuildRing?) {
		item?.reason = null
		build.buildItems.rings[index] = item ?: BuildRing(Data.getRingByName("None"))
		assignItem(build.buildItems.rings[index])
	}

	override fun getPrevious(): BuildRing? {
		var temp = build.buildItems.rings[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildRing?
		}
		return null
	}

	override fun selectItem(): BuildRing {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<RingSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildRing(Data.getRingByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}