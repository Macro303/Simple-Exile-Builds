package github.macro.ui.item.gem

import github.macro.core.build_info.Build
import github.macro.core.item.Items
import github.macro.core.item.gem.BuildGem
import github.macro.core.item.gem.Gem
import github.macro.ui.item.BaseItemEditor
import github.macro.ui.item.ItemSelectionModel
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemEditor(build: Build, buildItem: BuildGem, index: Int, private val equipment: String?) :
	BaseItemEditor<BuildGem, Gem>(build = build, buildItem = buildItem, index = index, columnCount = 6) {

	override val selectionModel = ItemSelectionModel(
		items = Items.GEM_LIST,
		selected = getBuildList()[index]
	)

	private fun getBuildList(): ArrayList<BuildGem> {
		return when (equipment) {
			"Weapons" -> build.buildGems.weapons
			"Body Armour" -> build.buildGems.bodyArmour
			"Helmet" -> build.buildGems.helmet
			"Gloves" -> build.buildGems.gloves
			"Boots" -> build.buildGems.boots
			else -> build.buildGems.weapons
		} as ArrayList<BuildGem>
	}

	override fun addItem(item: BuildGem?) {
		item?.reason = null
		getBuildList()[index] = item ?: BuildGem(Items.getGemByName("None"))
		assignItem(
			when (equipment) {
				"Weapons" -> build.buildGems.weapons
				"Body Armour" -> build.buildGems.bodyArmour
				"Helmet" -> build.buildGems.helmet
				"Gloves" -> build.buildGems.gloves
				"Boots" -> build.buildGems.boots
				else -> build.buildGems.weapons
			}[index]
		)
	}

	override fun getPrevious(): BuildGem? {
		var temp = getBuildList()[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGem
		}
		return null
	}

	override fun selectItem(): BuildGem {
		val scope = Scope()
		setInScope(selectionModel, scope)
		find<GemSelector>(scope).openModal(block = true, resizable = false)
		return selectionModel.selected ?: BuildGem(Items.getGemByName("None"))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}