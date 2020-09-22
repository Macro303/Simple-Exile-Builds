package github.macro.ui.items.rings

import github.macro.core.build_info.Build
import github.macro.core.items.rings.BuildRing
import github.macro.ui.AbstractViewerPane
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingViewerPane(build: Build, buildItem: BuildRing, index: Int) : AbstractViewerPane<BuildRing>(
	build = build,
	buildItem = buildItem,
	index = index,
	columnCount = 2
) {
	override fun getPrevious(): BuildRing? {
		var temp = build.buildItems.rings[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildRing?
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}