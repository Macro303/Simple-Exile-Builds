package github.macro.ui

import github.macro.Data
import github.macro.Util
import github.macro.build_info.*
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Aug-18
 */
class UIController : Controller() {
	private val selectorView by inject<BuildSelector>()
	private val buildViewerView by inject<BuildViewer>()
	private val buildEditorView by inject<BuildEditor>()
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
			gems = BuildGems(
				weapons = Util.getStartingGems(classTag),
				armour = mutableListOf(),
				helmet = mutableListOf(),
				gloves = mutableListOf(),
				boots = mutableListOf(),
				updates = mutableListOf()
			),
			gear = BuildGear(
				weapons = mutableListOf(),
				armour = Data.MISSING_EQUIPMENT,
				helmet = Data.MISSING_EQUIPMENT,
				gloves = Data.MISSING_EQUIPMENT,
				boots = Data.MISSING_EQUIPMENT,
				belt = Data.MISSING_EQUIPMENT,
				amulet = Data.MISSING_EQUIPMENT,
				rings = mutableListOf(),
				flasks = mutableListOf()
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
			gems = model.selectedBuild.gems,
			gear = model.selectedBuild.gear
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