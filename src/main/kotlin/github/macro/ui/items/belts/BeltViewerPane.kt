package github.macro.ui.items.belts

import github.macro.core.build_info.Build
import github.macro.core.items.belts.BuildBelt
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltViewerPane(build: Build) : AbstractViewerPane<BuildBelt>(
	build = build,
	buildItem = build.buildItems.belt,
	index = 0
) {
	override fun getPrevious(): BuildBelt? {
		var temp = build.buildItems.belt
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBelt?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}