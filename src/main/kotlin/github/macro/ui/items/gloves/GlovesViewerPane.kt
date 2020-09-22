package github.macro.ui.items.gloves

import github.macro.core.build_info.Build
import github.macro.core.items.gloves.BuildGloves
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesViewerPane(build: Build) : AbstractViewerPane<BuildGloves>(
	build = build,
	buildItem = build.buildItems.gloves,
	index = 0
) {
	override fun getPrevious(): BuildGloves? {
		var temp = build.buildItems.gloves
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGloves?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}