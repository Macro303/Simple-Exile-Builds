package github.macro.ui.items.flasks

import github.macro.core.Data
import github.macro.core.items.flasks.BuildFlask
import github.macro.core.items.flasks.ItemFlask
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskSelector : AbstractItemSelector<BuildFlask, ItemFlask>() {

	override fun updateSelection(selected: ItemFlask?) {
		selectedItem = BuildFlask(selected ?: Data.getFlask(null))
		var image = selectedItem!!.item.getFile()
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parent, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}