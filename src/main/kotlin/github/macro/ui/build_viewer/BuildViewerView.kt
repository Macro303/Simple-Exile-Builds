package github.macro.ui.build_viewer

import github.macro.Styles
import github.macro.Utils
import github.macro.ui.main.MainView
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2021-Apr-13.
 */
class BuildViewerView : View("Path of Taurewa") {
    private val model by inject<BuildViewerModel>()

    override val root = borderpane {
        prefWidth = Utils.UI_PREF_WIDTH
        prefHeight = Utils.UI_PREF_HEIGHT
        paddingAll = 10.0
        top {
            //region Header Pane
            paddingAll = 5.0
            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                label(text = "Build Viewer") {
                    addClass(Styles.title)
                }
                spacer { }
                vbox(spacing = 5.0, alignment = Pos.CENTER) {
                    paddingAll = 5.0
                    label(model.titleProperty) {
                        addClass(Styles.title)
                    }
                    hbox(spacing = 5.0, alignment = Pos.CENTER) {
                        paddingAll = 5.0
                        spacer { }
                        label(model.versionProperty) {
                            addClass(Styles.subtitle)
                        }
                        separator()
                        label(model.classProperty) {
                            addClass(Styles.subtitle)
                        }
                        separator()
                        label(model.ascendencyProperty) {
                            addClass(Styles.subtitle)
                        }
                        spacer { }
                        button(text = "Copy") {
                            addClass(Styles.sizedButton)
                            action {
//                            controller.copyBuild(oldView = this@BuildViewer)
                                LOGGER.info("Copying the build")
                            }
                        }
                        button(text = "Edit") {
                            addClass(Styles.sizedButton)
                            action {
//                            controller.editBuild(oldView = this@BuildViewer)
                                LOGGER.info("Editing the build")
                            }
                        }
                        button(text = "Delete") {
                            addClass(Styles.sizedButton)
                            action {
//                            controller.deleteBuild(oldView = this@BuildViewer)
                                LOGGER.info("Deleting the build")
                            }
                        }
                    }
                }
            }
            //endregion
        }
        center {
            paddingAll = 5.0
            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab(text = "Gems") {
                    tabpane {
                        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                        side = Side.LEFT
                        model.stages.forEach {
                            tab(it.name) {
                                scrollpane(fitToWidth = true) {
                                    hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                                    vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
                                    gridpane {
                                        row {
                                            label("Weapon/s") {
                                                addClass(Styles.subtitle)
                                                gridpaneConstraints {
                                                    columnSpan = 6
                                                    hAlignment = HPos.CENTER
                                                    margin = Insets(2.5)
                                                }
                                            }
                                        }
                                        row {
                                            it.weaponList.forEachIndexed { index, gem ->
                                                label(gem.name) {
                                                    gridpaneConstraints {
                                                        margin = Insets(2.5)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                tab(text = "Gear") {
                    scrollpane(fitToWidth = true) {
                        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                        gridpane {}
                    }
                }
                tab(text = "Other") {
                    scrollpane(fitToWidth = true) {
                        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                        vbox(spacing = 5.0, alignment = Pos.CENTER) {
                            label(text = "Other Details") {
                                addClass(Styles.subtitle)
                            }
                            textarea(model.detailsProperty) {
                                isEditable = false
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            find<MainView>().openWindow(owner = null, resizable = false)
            close()
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}