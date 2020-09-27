package github.macro.ui.item.gear.helmet

import github.macro.core.build_info.Build
import github.macro.core.item.gear.helmets.BuildHelmet
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class HelmetViewer(build: Build) : BaseGearViewer<BuildHelmet>(
	build = build,
	buildGear = build.buildGear.helmet,
	index = 0
) {
	override fun getPrevious(): BuildHelmet? {
		var temp = build.buildGear.helmet
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildHelmet
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}