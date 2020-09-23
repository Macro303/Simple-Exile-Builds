package github.macro.ui.items.boots

import github.macro.core.build_info.Build
import github.macro.core.items.boots.BuildBoots
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsViewerPane(build: Build) : AbstractViewerPane<BuildBoots>(
	build = build,
	buildItem = build.buildItems.boots,
	index = 0,
	imageWidth = 156.0,
	imageHeight = 156.0
) {
	override fun getPrevious(): BuildBoots? {
		var temp = build.buildItems.boots
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBoots?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}