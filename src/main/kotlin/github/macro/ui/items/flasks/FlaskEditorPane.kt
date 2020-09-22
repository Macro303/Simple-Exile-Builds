package github.macro.ui.items.flasks

import github.macro.core.Data
import github.macro.core.build_info.Build
import github.macro.core.items.flasks.BuildFlask
import github.macro.core.items.flasks.ItemFlask
import github.macro.ui.AbstractEditorPane
import github.macro.ui.SelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Sep-22
 */
class FlaskEditorPane(build: Build, buildItem: BuildFlask, index: Int) : AbstractEditorPane<BuildFlask, ItemFlask>(
	build = build,
	buildItem = buildItem,
	index = index,
	imageWidth = 47.0,
	imageHeight = 94.0,
	columnCount = 5
) {
	override val selectionModel = SelectionModel<BuildFlask, ItemFlask>(
		items = Data.FLASK_LIST,
		imageWidth = imageWidth,
		imageHeight = imageHeight
	)

	override fun addItem(item: BuildFlask?) {
		build.buildItems.flasks[index] = item ?: BuildFlask(Data.getFlask("None"))
		assignItem(build.buildItems.flasks[index])
	}

	override fun getPrevious(): BuildFlask? {
		var temp = build.buildItems.flasks[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildFlask?
		}
		return null
	}

	override fun selectItem(): BuildFlask {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<FlaskSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildFlask(Data.getFlask("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}