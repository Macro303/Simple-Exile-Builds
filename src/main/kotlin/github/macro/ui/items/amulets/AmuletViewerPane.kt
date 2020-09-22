package github.macro.ui.items.amulets

import github.macro.core.build_info.Build
import github.macro.core.items.amulets.BuildAmulet
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletViewerPane(build: Build) : AbstractViewerPane<BuildAmulet>(
	build = build,
	buildItem = build.buildItems.amulet,
	index = 0
) {
	override fun getPrevious(): BuildAmulet? {
		var temp = build.buildItems.amulet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildAmulet?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}