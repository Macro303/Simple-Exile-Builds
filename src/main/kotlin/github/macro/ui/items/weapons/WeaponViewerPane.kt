package github.macro.ui.items.weapons

import github.macro.core.build_info.Build
import github.macro.core.items.weapons.BuildWeapon
import github.macro.core.items.weapons.ItemWeapon
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponViewerPane(build: Build, buildItem: BuildWeapon, index: Int) : AbstractViewerPane<BuildWeapon>(
	build = build,
	buildItem = buildItem,
	index = index,
	columnCount = 2,
	imageWidth = (buildItem.item as ItemWeapon).type.imageWidth,
	imageHeight = (buildItem.item as ItemWeapon).type.imageHeight
) {
	override fun getPrevious(): BuildWeapon? {
		var temp = build.buildItems.weapons[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildWeapon?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}