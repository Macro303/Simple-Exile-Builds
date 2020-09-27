package github.macro.ui.item.gem

import github.macro.core.build_info.Build
import github.macro.core.item.gem.BuildGem
import github.macro.ui.item.BaseItemViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemViewer(build: Build, buildItem: BuildGem, index: Int, private val equipment: String?) :
	BaseItemViewer<BuildGem>(build = build, buildItem = buildItem, index = index) {

	override fun getPrevious(): BuildGem? {
		var temp = when (equipment) {
			"Weapons" -> build.buildGems.weapons
			"Body Armour" -> build.buildGems.bodyArmour
			"Helmet" -> build.buildGems.helmet
			"Gloves" -> build.buildGems.gloves
			"Boots" -> build.buildGems.boots
			else -> build.buildGems.weapons
		}[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGem
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}