package github.macro.ui.item.gear.ring

import github.macro.core.build_info.Build
import github.macro.core.item.gear.rings.BuildRing
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class RingViewer(build: Build, buildGear: BuildRing, index: Int) : BaseGearViewer<BuildRing>(
	build = build,
	buildGear = buildGear,
	index = index
) {
	override fun getPrevious(): BuildRing? {
		var temp = build.buildGear.rings[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildRing
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}