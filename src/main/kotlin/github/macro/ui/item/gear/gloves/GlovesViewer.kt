package github.macro.ui.item.gear.gloves

import github.macro.core.build_info.Build
import github.macro.core.item.gear.gloves.BuildGloves
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class GlovesViewer(build: Build) : BaseGearViewer<BuildGloves>(
	build = build,
	buildGear = build.buildGear.gloves,
	index = 0
) {
	override fun getPrevious(): BuildGloves? {
		var temp = build.buildGear.gloves
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGloves
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}