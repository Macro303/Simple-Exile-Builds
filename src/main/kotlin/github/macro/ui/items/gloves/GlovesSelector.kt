package github.macro.ui.items.gloves

import github.macro.core.Data
import github.macro.core.items.gloves.BuildGloves
import github.macro.core.items.gloves.ItemGloves
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesSelector : AbstractItemSelector<BuildGloves, ItemGloves>() {
	init {
		title = "Gloves Selector"
	}

	override fun updateSelection(selected: ItemGloves?) {
		selectedItem = BuildGloves(selected ?: Data.getGlovesByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}