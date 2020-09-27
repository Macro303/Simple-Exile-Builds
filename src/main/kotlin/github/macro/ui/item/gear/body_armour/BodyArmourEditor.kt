package github.macro.ui.item.gear.body_armour

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.body_armour.BodyArmour
import github.macro.core.item.gear.body_armour.BuildBodyArmour
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourEditor(build: Build) : BaseGearEditor<BuildBodyArmour, BodyArmour>(
	build = build,
	buildGear = build.buildGear.bodyArmour,
	index = 0,
	columnCount = 1
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.BODY_ARMOUR_LIST,
		selected = build.buildGear.bodyArmour
	)

	override fun addItem(item: BuildBodyArmour?) {
		item?.reason = null
		build.buildGear.bodyArmour = item ?: BuildBodyArmour(Items.getBodyArmourByName("None"))
		assignItem(build.buildGear.bodyArmour)
	}

	override fun getPrevious(): BuildBodyArmour? {
		var temp = build.buildGear.bodyArmour
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBodyArmour
		}
		return null
	}

	override fun selectItem(): BuildBodyArmour {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<BodyArmourSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildBodyArmour(Items.getBodyArmourByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}