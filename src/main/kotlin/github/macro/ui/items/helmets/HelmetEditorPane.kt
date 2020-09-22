package github.macro.ui.items.helmets

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.helmets.BuildHelmet
import github.macro.core.items.helmets.ItemHelmet
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetEditorPane(build: Build) : AbstractEditorPane<BuildHelmet, ItemHelmet>(
	build = build,
	buildItem = build.buildItems.helmet,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = SelectionModel<BuildHelmet, ItemHelmet>(Data.HELMET_LIST)

	override fun addItem(item: BuildHelmet?) {
		build.buildItems.helmet = item ?: BuildHelmet(Data.getHelmet("None"))
		assignItem(build.buildItems.helmet)
	}

	override fun getPrevious(): BuildHelmet? {
		var temp = build.buildItems.helmet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildHelmet?
		}
		return null
	}

	override fun selectItem(): BuildHelmet {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<HelmetSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildHelmet(Data.getHelmet("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}