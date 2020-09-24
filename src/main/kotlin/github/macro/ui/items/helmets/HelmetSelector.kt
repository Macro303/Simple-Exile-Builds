package github.macro.ui.items.helmets

import github.macro.core.Data
import github.macro.core.items.helmets.BuildHelmet
import github.macro.core.items.helmets.Helmet
import github.macro.ui.AbstractItemSelector
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetSelector : AbstractItemSelector<BuildHelmet, Helmet>() {
	init {
		title = "Helmet Selector"
	}

	override fun updateSelection(selected: Helmet?) {
		selectedItem = BuildHelmet(selected ?: Data.getHelmetByName(null))
		var image = File(File("resources", "Images"), selectedItem!!.item.id.substringAfterLast("/") + ".png")
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parentFile, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}