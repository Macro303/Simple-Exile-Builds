package github.macro.ui.item.gear.weapon

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gear.weapons.BuildWeapon
import github.macro.core.item.gear.weapons.Weapon
import github.macro.ui.item.ItemSelectionModel
import github.macro.ui.item.gear.BaseGearEditor
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponEditor(build: Build, buildGear: BuildWeapon, index: Int) : BaseGearEditor<BuildWeapon, Weapon>(
	build = build,
	buildGear = buildGear,
	index = index,
	columnCount = 2
) {
	override val selectionModel = ItemSelectionModel(
		items = Items.WEAPON_LIST,
		selected = build.buildGear.weapons[index]
	)

	override fun addItem(item: BuildWeapon?) {
		item?.reason = null
		build.buildGear.weapons[index] = item ?: BuildWeapon(Items.getWeaponByName("None"))
		assignItem(build.buildGear.weapons[index])
	}

	override fun getPrevious(): BuildWeapon? {
		var temp = build.buildGear.weapons[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildWeapon
		}
		return null
	}

	override fun selectItem(): BuildWeapon {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<WeaponSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildWeapon(Items.getWeaponByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}