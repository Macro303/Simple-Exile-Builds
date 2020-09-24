package github.macro.ui.items.body_armours

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.body_armours.BuildBodyArmour
import github.macro.core.items.body_armours.BodyArmour
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourEditorPane(build: Build) : AbstractEditorPane<BuildBodyArmour, BodyArmour>(
	build = build,
	buildItem = build.buildItems.bodyArmour,
	index = 0,
	columnCount = 1,
	imageWidth = 156.0,
	imageHeight = 234.0
) {
	override val selectionModel = SelectionModel<BuildBodyArmour, BodyArmour>(
		items = Data.BODY_ARMOUR_LIST,
		imageWidth = imageWidth,
		imageHeight = imageHeight
	)

	override fun addItem(item: BuildBodyArmour?) {
		item?.reason = null
		build.buildItems.bodyArmour = item ?: BuildBodyArmour(Data.getBodyArmourByName("None"))
		assignItem(build.buildItems.bodyArmour)
	}

	override fun getPrevious(): BuildBodyArmour? {
		var temp = build.buildItems.bodyArmour
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
		return selectionModel.selected ?: BuildBodyArmour(Data.getBodyArmourByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}