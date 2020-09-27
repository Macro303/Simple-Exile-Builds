package github.macro.ui.item.gear.body_armour

import github.macro.core.build_info.Build
import github.macro.core.item.gear.body_armour.BuildBodyArmour
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class BodyArmourViewer(build: Build) : BaseGearViewer<BuildBodyArmour>(
	build = build,
	buildGear = build.buildGear.bodyArmour,
	index = 0
) {
	override fun getPrevious(): BuildBodyArmour? {
		var temp = build.buildGear.bodyArmour
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildBodyArmour
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}