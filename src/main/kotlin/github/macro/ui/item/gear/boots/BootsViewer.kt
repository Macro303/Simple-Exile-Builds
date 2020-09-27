package github.macro.ui.item.gear.boots

import github.macro.core.build_info.Build
import github.macro.core.item.gear.boots.BuildBoots
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BootsViewer(build: Build) : BaseGearViewer<BuildBoots>(
	build = build,
	buildGear = build.buildGear.boots,
	index = 0
) {
	override fun getPrevious(): BuildBoots? {
		var temp = build.buildGear.boots
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBoots
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}