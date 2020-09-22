package github.macro.ui.gems

import github.macro.Util
import github.macro.core.build_info.Build
import github.macro.core.gems.BuildGem
import github.macro.core.gems.ItemGem
import github.macro.core.gems.reward.RewardType
import github.macro.ui.AbstractViewerPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemViewerPane(build: Build, buildGem: BuildGem, index: Int, private val equipment: String?) :
	AbstractViewerPane<BuildGem>(build = build, buildItem = buildGem, index = index) {

	override fun getPrevious(): BuildGem? {
		var temp = when (equipment) {
			"Weapons" -> build.buildGems.weapons
			"Body Armour" -> build.buildGems.bodyArmour
			"Helmet" -> build.buildGems.helmet
			"Gloves" -> build.buildGems.gloves
			"Boots" -> build.buildGems.boots
			else -> build.buildGems.weapons
		}[index]
		while (temp.nextItem != null) {
			if (temp.nextItem == buildItem)
				return temp
			temp = temp.nextItem as BuildGem?
		}
		return null
	}

	override fun assignItem(newItem: BuildGem) {
		super.assignItem(newItem)

		style {
			borderColor += box(c(Util.colourToHex((buildItem.item as ItemGem).colour)))
			borderStyle += BorderStrokeStyle(
				StrokeType.CENTERED,
				StrokeLineJoin.ROUND,
				StrokeLineCap.ROUND,
				0.0,
				0.0,
				listOf(5.0, 5.0)
			)
			borderWidth += box(2.px)
		}

		var temp = (buildItem.item as ItemGem).acquisition.rewards.joinToString(separator = "\n") {
			var text = "Act ${it.act} - ${it.quest}"
			if (it.rewardType == RewardType.VENDOR)
				text += " (${it.vendor})"
			text
		}.trim()
		if (temp.isBlank())
			temp = "Not available as a reward"
		rewards = temp
		colour = Paint.valueOf(Util.colourToHex((buildItem.item as ItemGem).colour))
	}

	companion object {
		private val LOGGER = LogManager.getLogger()
	}
}