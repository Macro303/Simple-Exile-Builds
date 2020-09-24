package github.macro.ui.items.boots

import github.macro.core.Data
import github.macro.core.items.boots.BuildBoots
import github.macro.core.items.boots.Boots
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsSelector : AbstractItemSelector<BuildBoots, Boots>() {
	init {
		title = "Boot Selector"
	}

	override fun updateSelection(selected: Boots?) {
		selectedItem = BuildBoots(selected ?: Data.getBootsByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}