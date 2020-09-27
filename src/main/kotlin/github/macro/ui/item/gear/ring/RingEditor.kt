package github.macro.ui.item.gear.ring

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.rings.BuildRing
import github.macro.core.item.gear.rings.Ring
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingEditor(build: Build, buildGear: BuildRing, index: Int) : BaseGearEditor<BuildRing, Ring>(
	build = build,
	buildGear = buildGear,
	index = index,
	columnCount = 2
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.RING_LIST,
		selected = build.buildGear.rings[index]
	)

	override fun addItem(item: BuildRing?) {
		item?.reason = null
		build.buildGear.rings[index] = item ?: BuildRing(Items.getRingByName("None"))
		assignItem(build.buildGear.rings[index])
	}

	override fun getPrevious(): BuildRing? {
		var temp = build.buildGear.rings[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildRing
		}
		return null
	}

	override fun selectItem(): BuildRing {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<RingSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildRing(Items.getRingByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}