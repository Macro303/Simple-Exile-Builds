package github.macro.ui.items.flasks

import github.macro.core.Data
import github.macro.core.items.flasks.BuildFlask
import github.macro.core.items.flasks.Flask
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskSelector : AbstractItemSelector<BuildFlask, Flask>() {
	init {
		title = "Flask Selector"
	}

	override fun updateSelection(selected: Flask?) {
		selectedItem = BuildFlask(selected ?: Data.getFlaskByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}