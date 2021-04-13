package github.macro

import github.macro.ui.main.MainView
import javafx.scene.image.Image
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.util.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Launcher : App(Image(Utils.getResource("logo.png")), MainView::class, Styles::class) {
    init {
        checkLogLevels()
        LOGGER.info("Welcome to Path of Taurewa")
        importStylesheet(Utils.getResource("Modena-Dark.css"))
        /*if (Settings.INSTANCE.useDarkMode) {
            importStylesheet(Utils.getResource("Modena-Dark.css"))
            importStylesheet(Utils.getResource("Custom-Dark.css"))
            importStylesheet(Utils.getResource("Temp-Dark.css"))
        }*/
        FX.locale = Locale.ENGLISH
        reloadStylesheetsOnFocus()
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
    launch<Launcher>(*args)
}

class Styles : Stylesheet() {
    private val fontBold = Font.loadFont(Utils.getResourceStream("fonts/Fontin-Bold.otf"), 12.0)
    private val fontRegular = Font.loadFont(Utils.getResourceStream("fonts/Fontin-Regular.otf"), 12.0)
    private val fontSmallCaps = Font.loadFont(Utils.getResourceStream("fonts/Fontin-SmallCaps.otf"), 12.0)

    init {
        root {
            fontFamily = fontRegular.family
            fontSize = 13.px
        }
        button {
            fontFamily = fontSmallCaps.family
        }
        tabLabel {
            fontFamily = fontSmallCaps.family
            fontSize = 16.px
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
            minWidth = 100.px
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