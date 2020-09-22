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

	override fun updateSelection(selected: ItemAmulet?) {
		selectedItem = BuildAmulet(selected ?: Data.getAmulet(null))
		var image = selectedItem!!.item.getFile()
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parent, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}