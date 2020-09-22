package github.macro.ui.items.weapons

import github.macro.core.Data
import github.macro.core.items.weapons.BuildWeapon
import github.macro.core.items.weapons.ItemWeapon
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponSelector : AbstractItemSelector<BuildWeapon, ItemWeapon>() {

	override fun updateSelection(selected: ItemWeapon?) {
		selectedItem = BuildWeapon(selected ?: Data.getWeapon(null))
		var image = selectedItem!!.item.getFile()
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parent, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}