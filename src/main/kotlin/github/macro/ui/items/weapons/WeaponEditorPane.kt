package github.macro.ui.items.weapons

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.weapons.BuildWeapon
import github.macro.core.items.weapons.ItemWeapon
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponEditorPane(build: Build, buildItem: BuildWeapon, index: Int) : AbstractEditorPane<BuildWeapon, ItemWeapon>(
	build = build,
	buildItem = buildItem,
	index = index,
	columnCount = 2,
	imageWidth = (buildItem.item as ItemWeapon).type.imageWidth,
	imageHeight = (buildItem.item as ItemWeapon).type.imageHeight
) {
	override val selectionModel = SelectionModel<BuildWeapon, ItemWeapon>(
		items = Data.WEAPON_LIST,
		imageWidth = imageWidth,
		imageHeight = imageHeight
	)

	override fun addItem(item: BuildWeapon?) {
		item?.reason = null
		build.buildItems.weapons[index] = item ?: BuildWeapon(Data.getWeaponByName("None"))
		assignItem(build.buildItems.weapons[index])
	}

	override fun getPrevious(): BuildWeapon? {
		var temp = build.buildItems.weapons[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildWeapon?
		}
		return null
	}

	override fun selectItem(): BuildWeapon {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<WeaponSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildWeapon(Data.getWeaponByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}