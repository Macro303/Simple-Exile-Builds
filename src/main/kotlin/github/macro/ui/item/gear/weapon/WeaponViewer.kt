package github.macro.ui.item.gear.weapon

import github.macro.core.build_info.Build
import github.macro.core.item.gear.weapons.BuildWeapon
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class WeaponViewer(build: Build, buildGear: BuildWeapon, index: Int) : BaseGearViewer<BuildWeapon>(
	build = build,
	buildGear = buildGear,
	index = index
) {
	override fun getPrevious(): BuildWeapon? {
		var temp = build.buildGear.weapons[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildWeapon
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}