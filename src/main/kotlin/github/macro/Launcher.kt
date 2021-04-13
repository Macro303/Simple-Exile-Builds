package github.macro

import com.charleskorn.kaml.Yaml
import github.macro.core.Ascendency
import github.macro.core.BuildInfo
import github.macro.core.ClassTag
import github.macro.core.pantheon.MajorPantheon
import github.macro.core.pantheon.MinorPantheon
import github.macro.settings.Settings
import github.macro.ui.MainView
import javafx.scene.image.Image
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import kotlinx.serialization.decodeFromString
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Launcher : App(MainView::class, Styles::class) {
	init {
		checkLogLevels()
		LOGGER.info("Welcome to Path of Taurewa")
		if (Settings.INSTANCE.useDarkMode) {
//			importStylesheet(Launcher::class.java.getResource("Modena-Dark.css").toExternalForm())
			importStylesheet(Launcher::class.java.getResource("Custom-Dark.css")!!.toExternalForm())
		}
		FX.locale = Locale.ENGLISH
		reloadStylesheetsOnFocus()
		addStageIcon(Image(Launcher::class.java.getResource("logo.png")!!.toExternalForm()))
	}

	private fun checkLogLevels() {
		Level.values().sorted().forEach {
			LOGGER.log(it, "{} is Visible", it.name())
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Launcher::class.java)
	}
}

fun main(vararg args: String) {
	println("Launching Application")
	var build = BuildInfo("Test", "Test Build", ClassTag.DUELIST, Ascendency.ASCENDANT, emptyList(), emptyList(), MajorPantheon.ARAKAALI, MinorPantheon.ABBERATH)
	build.save()
	println(build)
	build = Files.newBufferedReader(Paths.get("builds", build.filename)).use {
		Yaml.default.decodeFromString<BuildInfo>(it.readText())
	}
	println(build)
	launch<Launcher>(*args)
}

class Styles : Stylesheet() {
	private val fontBold = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-Bold.otf")!!.openStream(), 12.0)
	private val fontRegular = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-Regular.otf")!!.openStream(), 12.0)
	private val fontSmallCaps = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-SmallCaps.otf")!!.openStream(), 12.0)

	init {
		root {
			fontFamily = fontRegular.family
			fontSize = 13.px
		}
		tooltip {
			fontFamily = fontRegular.family
			fontSize = 13.px
		}
		button {
			fontFamily = fontSmallCaps.family
			fontSize = 13.px
			minWidth = 20.px
		}
		title {
			fontFamily = fontBold.family
			fontSize = 28.px
			fontWeight = FontWeight.BOLD
		}
		subtitle {
			fontFamily = fontBold.family
			fontSize = 18.px
			fontWeight = FontWeight.BOLD
		}
		sizedButton {
			minWidth = 75.px
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Styles::class.java)

		val tooltip by cssclass()
		val title by cssclass()
		val subtitle by cssclass()
		val sizedButton by cssclass()
	}
}