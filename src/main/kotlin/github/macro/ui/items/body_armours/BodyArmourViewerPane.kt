package github.macro.ui.items.body_armours

import github.macro.core.build_info.Build
import github.macro.core.items.body_armours.BuildBodyArmour
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourViewerPane(build: Build) : AbstractViewerPane<BuildBodyArmour>(
	build = build,
	buildItem = build.buildItems.bodyArmour,
	index = 0
) {
	override fun getPrevious(): BuildBodyArmour? {
		var temp = build.buildItems.bodyArmour
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBodyArmour?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}