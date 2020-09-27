package github.macro.ui.item.gear.flask

import github.macro.core.build_info.Build
import github.macro.core.item.gear.flask.BuildFlask
import github.macro.ui.item.gear.BaseGearViewer
import org.apache.logging.log4j.LogManager

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskViewer(build: Build, buildItem: BuildFlask, index: Int) : BaseGearViewer<BuildFlask>(
	build = build,
	buildGear = buildItem,
	index = index
) {
	override fun getPrevious(): BuildFlask? {
		var temp = build.buildGear.flasks[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildFlask
		}
		return null
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}