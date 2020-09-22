package github.macro.ui.items.flasks

import github.macro.core.build_info.Build
import github.macro.core.items.flasks.BuildFlask
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskViewerPane(build: Build, buildItem: BuildFlask, index: Int) : AbstractViewerPane<BuildFlask>(
	build = build,
	buildItem = buildItem,
	index = index,
	imageWidth = 47.0,
	imageHeight = 94.0,
	columnCount = 5
) {
	override fun getPrevious(): BuildFlask? {
		var temp = build.buildItems.flasks[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildFlask?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}