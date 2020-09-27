package github.macro.ui.item.gear.weapon

import github.macro.core.item.Items
import github.macro.core.item.gear.weapons.BuildWeapon
import github.macro.core.item.gear.weapons.Weapon
import github.macro.ui.item.BaseItemSelector

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponSelector : BaseItemSelector<BuildWeapon, Weapon>() {
	init {
		title = "Weapon Selector"
	}

	override fun getSelected(): BuildWeapon? = BuildWeapon(model.selectedItem ?: Items.getWeaponByName(null))
}