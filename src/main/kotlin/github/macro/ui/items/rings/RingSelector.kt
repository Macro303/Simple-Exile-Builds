package github.macro.ui.items.rings

import github.macro.core.Data
import github.macro.core.items.rings.BuildRing
import github.macro.core.items.rings.Ring
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingSelector : AbstractItemSelector<BuildRing, Ring>() {
	init {
		title = "Ring Selector"
	}

	override fun updateSelection(selected: Ring?) {
		selectedItem = BuildRing(selected ?: Data.getRingByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}