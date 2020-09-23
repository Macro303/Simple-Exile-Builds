package github.macro.ui.items.amulets

import github.macro.core.Data
import github.macro.core.items.amulets.BuildAmulet
import github.macro.core.items.amulets.ItemAmulet
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletSelector : AbstractItemSelector<BuildAmulet, ItemAmulet>() {
	init {
		title = "Amulet Selector"
	}

	override fun updateSelection(selected: ItemAmulet?) {
		selectedItem = BuildAmulet(selected ?: Data.getAmuletByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}