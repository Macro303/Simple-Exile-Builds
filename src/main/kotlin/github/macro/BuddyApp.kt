package github.macro

import github.macro.ui.selector.Selector
import javafx.scene.text.FontWeight
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuddyApp : App(Selector::class, Styles::class) {
	init {
		importStylesheet(BuddyApp::class.java.getResource("Dark-Theme.css").toExternalForm())
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuddyApp::class.java)
	}
}

class Styles : Stylesheet() {
	init {
		/*root{
			fontSize = 10.pt
		}*/
		title {
			fontSize = 35.pt
			fontWeight = FontWeight.BOLD
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Styles::class.java)
		val title by cssid()
	}
}