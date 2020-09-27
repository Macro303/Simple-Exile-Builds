package github.macro.ui.item.gear.gloves

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.gloves.BuildGloves
import github.macro.core.item.gear.gloves.Gloves
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesEditor(build: Build) : BaseGearEditor<BuildGloves, Gloves>(
	build = build,
	buildGear = build.buildGear.gloves,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.GLOVES_LIST,
		selected = build.buildGear.gloves
	)

	override fun addItem(item: BuildGloves?) {
		item?.reason = null
		build.buildGear.gloves = item ?: BuildGloves(Items.getGlovesByName("None"))
		assignItem(build.buildGear.gloves)
	}

	override fun getPrevious(): BuildGloves? {
		var temp = build.buildGear.gloves
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGloves
		}
		return null
	}

	override fun selectItem(): BuildGloves {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<GlovesSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildGloves(Items.getGlovesByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}