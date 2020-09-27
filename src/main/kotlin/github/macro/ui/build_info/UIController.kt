package github.macro.ui.build_info

import github.macro.Util
import github.macro.core.build_info.Ascendency
import github.macro.core.build_info.Build
import github.macro.core.build_info.ClassTag
import github.macro.core.item.gem.BuildGem
import github.macro.core.build_info.BuildGemMap
import github.macro.core.build_info.BuildGearMap
import github.macro.core.item.Items
import github.macro.core.item.gear.amulet.BuildAmulet
import github.macro.core.item.gear.body_armour.BuildBodyArmour
import github.macro.core.item.gear.belt.BuildBelt
import github.macro.core.item.gear.boots.BuildBoots
import github.macro.core.item.gear.flask.BuildFlask
import github.macro.core.item.gear.gloves.BuildGloves
import github.macro.core.item.gear.helmets.BuildHelmet
import github.macro.core.item.gear.weapons.BuildWeapon
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
			buildGear = BuildGearMap(
				weapons = Util.getStartingWeapons(classTag).map { BuildWeapon(it) } as ArrayList<BuildWeapon>,
				bodyArmour = BuildBodyArmour(Items.getBodyArmourByName("None")),
				helmet = BuildHelmet(Items.getHelmetByName("None")),
				gloves = BuildGloves(Items.getGlovesByName("None")),
				boots = BuildBoots(Items.getBootsByName("None")),
				belt = BuildBelt(Items.getBeltByName("None")),
				amulet = BuildAmulet(Items.getAmuletByName("None")),
				rings = arrayListOf(),
				flasks = Util.getStartingFlasks().map { BuildFlask(it) } as ArrayList<BuildFlask>
			),
			details = null
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
			buildGear = model.selectedBuild.buildGear,
			details = model.selectedBuild.details
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