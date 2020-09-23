package github.macro.ui.gems

import github.macro.core.Data
import github.macro.core.gems.BuildGem
import github.macro.core.gems.ItemGem
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Aug-18
 */
class GemSelector : AbstractItemSelector<BuildGem, ItemGem>() {
	init {
		title = "Gem Selector"
	}

	override fun updateSelection(selected: ItemGem?) {
		selectedItem = BuildGem(selected ?: Data.getGemByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}