package github.buried_in_code.ui.build_viewer

import github.buried_in_code.Styles
import github.buried_in_code.Utils
import github.buried_in_code.Utils.cleanName
import github.buried_in_code.core.Bandit
import github.buried_in_code.ui.build_editor.BuildEditorModel
import github.buried_in_code.ui.build_editor.BuildEditorView
import github.buried_in_code.ui.main.MainView
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Buried-In-Code on 2021-Apr-13.
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
                                val scope = Scope()
                                setInScope(
                                    BuildViewerModel(model.build.copy(title = model.build.title + " Copy")),
                                    scope
                                )
                                find<BuildViewerView>(scope).openWindow(owner = null, resizable = false)
                                close()
                            }
                        }
                        button(text = "Edit") {
                            addClass(Styles.sizedButton)
                            action {
                                val scope = Scope()
                                setInScope(BuildEditorModel(model.build), scope)
                                find<BuildEditorView>(scope).openWindow(owner = null, resizable = false)
                                close()
                            }
                        }
                        button(text = "Delete") {
                            addClass(Styles.sizedButton)
                            action {
                                model.build.delete()
                                find<MainView>().openWindow(owner = null, resizable = false)
                                close()
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
                //region Gems Tab
                tab(text = "Gems") {
                    gridpane {
                        //region Weapon Gems
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
                            model.gemsWeaponSlot.forEach {
                                add(ItemViewerView(it).gridpaneConstraints {
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                })
                            }
                        }
                        //endregion
                        //region Helmet Gems
                        row {
                            label("Helmet") {
                                addClass(Styles.subtitle)
                                gridpaneConstraints {
                                    columnSpan = 6
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                }
                            }
                        }
                        row {
                            model.gemsHelmetSlot.forEach {
                                add(ItemViewerView(it).gridpaneConstraints {
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                })
                            }
                        }
                        //endregion
                        //region Body Armour Gems
                        row {
                            label("Body Armour") {
                                addClass(Styles.subtitle)
                                gridpaneConstraints {
                                    columnSpan = 6
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                }
                            }
                        }
                        row {
                            model.gemsBodyArmourSlot.forEach {
                                add(ItemViewerView(it).gridpaneConstraints {
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                })
                            }
                        }
                        //endregion
                        //region Gloves Gems
                        row {
                            label("Gloves") {
                                addClass(Styles.subtitle)
                                gridpaneConstraints {
                                    columnSpan = 6
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                }
                            }
                        }
                        row {
                            model.gemsGlovesSlot.forEach {
                                add(ItemViewerView(it).gridpaneConstraints {
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                })
                            }
                        }
                        //endregion
                        //region Boots Gems
                        row {
                            label("Boots") {
                                addClass(Styles.subtitle)
                                gridpaneConstraints {
                                    columnSpan = 6
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                }
                            }
                        }
                        row {
                            model.gemsBootsSlot.forEach {
                                add(ItemViewerView(it).gridpaneConstraints {
                                    hAlignment = HPos.CENTER
                                    margin = Insets(2.5)
                                })
                            }
                        }
                        //endregion
                    }
                }
                //endregion
                //region Gear Tab
                tab(text = "Gear") {
                    isDisable = true
                    scrollpane(fitToWidth = true) {
                        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                        gridpane {}
                    }
                }
                //endregion
                //region Other Tab
                tab(text = "Other") {
                    scrollpane(fitToWidth = true) {
                        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                        vbox(spacing = 5.0, alignment = Pos.CENTER) {
                            paddingAll = 5.0
                            //region Bandits
                            label(text = "Kill Bandits") {
                                addClass(Styles.subtitle)
                            }
                            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                                paddingAll = 5.0
                                Bandit.values().forEach {
                                    checkbox(text = it.cleanName()) {
                                        isSelected = model.banditList.contains(it)
                                        isDisable = true
                                    }
                                }
                            }
                            //endregion
                            //region Ascendency
                            label(text = "Ascendency") {
                                addClass(Styles.subtitle)
                            }
                            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                                paddingAll = 5.0
                                label(model.majorPantheonProperty)
                                separator()
                                label(model.minorPantheonProperty)
                            }
                            //endregion
                            //region Other
                            label(text = "Other Details") {
                                addClass(Styles.subtitle)
                            }
                            textarea(model.detailsProperty) {
                                isEditable = false
                                isDisable = true
                            }
                            //endregion
                        }
                    }
                }
                //endregion
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