package github.macro.ui.items.belts

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.belts.BuildBelt
import github.macro.core.items.belts.Belt
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltEditorPane(build: Build) : AbstractEditorPane<BuildBelt, Belt>(
	build = build,
	buildItem = build.buildItems.belt,
	index = 0,
	columnCount = 1,
	imageWidth = 156.0
) {
	override val selectionModel = SelectionModel<BuildBelt, Belt>(
		items = Data.BELT_LIST,
		imageWidth = imageWidth
	)

	override fun addItem(item: BuildBelt?) {
		item?.reason = null
		build.buildItems.belt = item ?: BuildBelt(Data.getBeltByName("None"))
		assignItem(build.buildItems.belt)
	}

	override fun getPrevious(): BuildBelt? {
		var temp = build.buildItems.belt
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBelt?
		}
		return null
	}

	override fun selectItem(): BuildBelt {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BeltSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBelt(Data.getBeltByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}