package github.macro.ui.item.gear.amulet

import github.macro.core.build_info.Build
import github.macro.core.item.gear.amulet.BuildAmulet
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class AmuletViewer(build: Build) : BaseGearViewer<BuildAmulet>(
	build = build,
	buildGear = build.buildGear.amulet,
	index = 0
) {
	override fun getPrevious(): BuildAmulet? {
		var temp = build.buildGear.amulet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildAmulet
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}