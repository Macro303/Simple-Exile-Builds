package github.macro.ui.build_info

import github.macro.Util
import github.macro.core.Data
import github.macro.core.build_info.Ascendency
import github.macro.core.build_info.Build
import github.macro.core.build_info.ClassTag
import github.macro.core.gems.BuildGem
import github.macro.core.gems.BuildGemMap
import github.macro.core.items.BuildItemMap
import github.macro.core.items.amulets.BuildAmulet
import github.macro.core.items.body_armours.BuildBodyArmour
import github.macro.core.items.belts.BuildBelt
import github.macro.core.items.boots.BuildBoots
import github.macro.core.items.flasks.BuildFlask
import github.macro.core.items.gloves.BuildGloves
import github.macro.core.items.helmets.BuildHelmet
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Aug-18
 */
class UIController : Controller() {
	private val model by inject<UIModel>()

	fun loadBuilds() {
		model.loadBuilds()
	}

	fun viewBuild(oldView: View) {
		LOGGER.info("Viewing Build: ${model.selectedBuild.display}")
		val scope = Scope()
		setInScope(model, scope)
		find<BuildViewer>(scope).openWindow(owner = null, resizable = false)
		oldView.close()
	}

	fun selectBuild(oldView: View) {
		find<BuildSelector>().openWindow(owner = null, resizable = false)
		oldView.close()
	}

	fun updateClass(classTag: ClassTag) {
		model.selectedClass(classTag)
	}

	fun createBuild(
		oldView: View,
		version: String,
		name: String,
		classTag: ClassTag,
		ascendency: Ascendency
	) {
		model.selectedBuild = Build(
			version = version,
			name = name,
			classTag = classTag,
			ascendency = ascendency,
			buildGems = BuildGemMap(
				weapons = Util.getStartingGems(classTag).map { BuildGem(it) },
				bodyArmour = mutableListOf(),
				helmet = mutableListOf(),
				gloves = mutableListOf(),
				boots = mutableListOf()
			),
			buildItems = BuildItemMap(
				weapons = mutableListOf(),
				bodyArmour = BuildBodyArmour(Data.getBodyArmour("None")),
				helmet = BuildHelmet(Data.getHelmet("None")),
				gloves = BuildGloves(Data.getGloves("None")),
				boots = BuildBoots(Data.getBoots("None")),
				belt = BuildBelt(Data.getBelt("None")),
				amulet = BuildAmulet(Data.getAmulet("None")),
				rings = mutableListOf(),
				flasks = Util.getStartingFlasks().map { BuildFlask(it) }
			)
		)
		LOGGER.info("Creating Build: ${model.selectedBuild.display}")
		model.selectedBuild.save()
		viewBuild(oldView)
	}

	fun copyBuild(oldView: View) {
		val copiedBuild = Build(
			version = model.selectedBuild.version,
			name = model.selectedBuild.name + " Copy",
			classTag = model.selectedBuild.classTag,
			ascendency = model.selectedBuild.ascendency,
			buildGems = model.selectedBuild.buildGems,
			buildItems = model.selectedBuild.buildItems
		)
		copiedBuild.save()
		LOGGER.info("Changing Build: ${model.selectedBuild.display} => ${copiedBuild.display}")
		model.selectedBuild = copiedBuild
		viewBuild(oldView)
	}

	fun editBuild(oldView: View) {
		LOGGER.info("Editing Build: ${model.selectedBuild.display}")
		val scope = Scope()
		setInScope(model, scope)
		find<BuildEditor>(scope).openWindow(owner = null, resizable = false)
		oldView.close()
	}

	fun saveBuild(
		oldView: View,
		version: String,
		name: String,
		classTag: ClassTag,
		ascendency: Ascendency
	) {
		model.selectedBuild.also {
			it.delete()
			it.version = version
			it.name = name
			it.classTag = classTag
			it.ascendency = ascendency
			LOGGER.info("Saving Build: ${it.display}")
		}.save()
		viewBuild(oldView)
	}

	fun deleteBuild(oldView: View) {
		val temp = model.selectedBuild
		model.selectedBuild = null
		temp.delete()
		LOGGER.info("Deleting Build: ${temp.display}")
		selectBuild(oldView = oldView)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(UIController::class.java)
	}
}