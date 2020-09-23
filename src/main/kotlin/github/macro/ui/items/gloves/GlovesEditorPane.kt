package github.macro.ui.items.gloves

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.gloves.BuildGloves
import github.macro.core.items.gloves.ItemGloves
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesEditorPane(build: Build) : AbstractEditorPane<BuildGloves, ItemGloves>(
	build = build,
	buildItem = build.buildItems.gloves,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = SelectionModel<BuildGloves, ItemGloves>(Data.GLOVES_LIST)

	override fun addItem(item: BuildGloves?) {
		item?.reason = null
		build.buildItems.gloves = item ?: BuildGloves(Data.getGloves("None"))
		assignItem(build.buildItems.gloves)
	}

	override fun getPrevious(): BuildGloves? {
		var temp = build.buildItems.gloves
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGloves?
		}
		return null
	}

	override fun selectItem(): BuildGloves {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<GlovesSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildGloves(Data.getGloves("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}