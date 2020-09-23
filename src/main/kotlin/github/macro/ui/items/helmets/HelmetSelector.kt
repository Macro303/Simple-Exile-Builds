package github.macro.ui.items.helmets

import github.macro.core.Data
import github.macro.core.items.helmets.BuildHelmet
import github.macro.core.items.helmets.ItemHelmet
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetSelector : AbstractItemSelector<BuildHelmet, ItemHelmet>() {

	override fun updateSelection(selected: ItemHelmet?) {
		selectedItem = BuildHelmet(selected ?: Data.getHelmetByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}