package github.macro.ui

import github.macro.Launcher
import github.macro.Styles
import javafx.geometry.Pos
import tornadofx.*

/**
 * Created by Macro303 on 2021-Apr-13.
 */
class ViewBuildView : View("Path of Taurewa") {
    private val model by inject<BuildModel>()

    override val root = borderpane {
        prefWidth = 1000.0
        prefHeight = 900.0
        paddingAll = 10.0
        top {
            hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
                imageview(Launcher::class.java.getResource("logo.png")!!.toExternalForm(), lazyload = true) {
                    fitWidth = 100.0
                    fitHeight = 100.0
                }
                label(text = "Path of Taurewa") {
                    addClass(Styles.title)
                }
                spacer { }
                spacer { }
                label(model.titleProperty) {
                    addClass(Styles.title)
                }
                spacer { }
                button("Exit") {
                    addClass(Styles.sizedButton)
                }
            }
        }
        center {

        }
        left {

        }
        right {

        }
        bottom {

        }
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            find<MainView>().openWindow(owner = null, resizable = false)
            close()
        }
    }
}