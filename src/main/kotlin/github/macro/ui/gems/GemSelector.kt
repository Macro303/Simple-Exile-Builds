package github.macro.ui.gems

import github.macro.core.Data
import github.macro.core.gems.BuildGem
import github.macro.core.gems.ItemGem
import github.macro.ui.AbstractItemSelector
import org.apache.logging.log4j.LogManager
import java.io.File

/**
 * Created by Macro303 on 2020-Aug-18
 */
class GemSelector : AbstractItemSelector<BuildGem, ItemGem>() {
	private val LOGGER = LogManager.getLogger()

	override fun updateSelection(selected: ItemGem?) {
		selectedItem = BuildGem(selected ?: Data.getGem(null))
		var image = selectedItem!!.item.getFile()
		if (selectedItem!!.item.name != "None" && !image.exists())
			image = File(image.parent, "placeholder.png")
		imageUrl = "file:${image.path}"
	}
}