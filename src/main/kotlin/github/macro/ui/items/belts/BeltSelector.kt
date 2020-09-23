package github.macro.ui.items.belts

import github.macro.core.Data
import github.macro.core.items.belts.BuildBelt
import github.macro.core.items.belts.ItemBelt
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltSelector : AbstractItemSelector<BuildBelt, ItemBelt>() {
	init {
		title = "Belt Selector"
	}

	override fun updateSelection(selected: ItemBelt?) {
		selectedItem = BuildBelt(selected ?: Data.getBeltByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}