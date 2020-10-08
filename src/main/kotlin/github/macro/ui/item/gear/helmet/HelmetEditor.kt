package github.macro.ui.item.gear.helmet

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.helmets.BuildHelmet
import github.macro.core.item.gear.helmets.Helmet
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetEditor(build: Build) : BaseGearEditor<BuildHelmet, Helmet>(
	build = build,
	buildGear = build.buildGear.helmet,
	index = 0
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.HELMET_LIST,
		selected = build.buildGear.helmet
	)

	override fun addItem(item: BuildHelmet?) {
		item?.reason = null
		build.buildGear.helmet = item ?: BuildHelmet(Items.getHelmetByName("None"))
		assignItem(build.buildGear.helmet)
	}

	override fun getPrevious(): BuildHelmet? {
		var temp = build.buildGear.helmet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildHelmet
		}
		return null
	}

	override fun selectItem(): BuildHelmet {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<HelmetSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildHelmet(Items.getHelmetByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}