package github.macro.ui.item.gear.belt

import github.macro.core.build_info.Build
import github.macro.core.item.gear.belt.BuildBelt
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BeltViewer(build: Build) : BaseGearViewer<BuildBelt>(
	build = build,
	buildGear = build.buildGear.belt,
	index = 0
) {
	override fun getPrevious(): BuildBelt? {
		var temp = build.buildGear.belt
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBelt
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}