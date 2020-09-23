package github.macro.ui.items.helmets

import github.macro.core.build_info.Build
import github.macro.core.items.helmets.BuildHelmet
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetViewerPane(build: Build) : AbstractViewerPane<BuildHelmet>(
	build = build,
	buildItem = build.buildItems.helmet,
	index = 0,
	imageWidth = 156.0,
	imageHeight = 156.0
) {
	override fun getPrevious(): BuildHelmet? {
		var temp = build.buildItems.helmet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildHelmet?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}