package github.macro.ui.items.body_armours

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.body_armours.BuildBodyArmour
import github.macro.core.items.body_armours.ItemBodyArmour
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourEditorPane(build: Build) : AbstractEditorPane<BuildBodyArmour, ItemBodyArmour>(
	build = build,
	buildItem = build.buildItems.armour,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = SelectionModel<BuildBodyArmour, ItemBodyArmour>(Data.BODY_ARMOUR_LIST)

	override fun addItem(item: BuildBodyArmour?) {
		build.buildItems.armour = item ?: BuildBodyArmour(Data.getBodyArmour("None"))
		assignItem(build.buildItems.armour)
	}

	override fun getPrevious(): BuildBodyArmour? {
		var temp = build.buildItems.armour
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBodyArmour?
		}
		return null
	}

	override fun selectItem(): BuildBodyArmour {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BodyArmourSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBodyArmour(Data.getBodyArmour("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}