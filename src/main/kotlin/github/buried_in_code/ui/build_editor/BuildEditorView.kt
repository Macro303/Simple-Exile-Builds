package github.buried_in_code.ui.build_editor

import github.buried_in_code.Styles
import github.buried_in_code.Utils
import github.buried_in_code.Utils.cleanName
import github.buried_in_code.core.Ascendency
import github.buried_in_code.core.Bandit
import github.buried_in_code.core.ClassTag
import github.buried_in_code.core.Gem
import github.buried_in_code.ui.build_viewer.BuildViewerModel
import github.buried_in_code.ui.build_viewer.BuildViewerView
import github.buried_in_code.ui.gem_selector.GemSelectorModel
import github.buried_in_code.ui.gem_selector.GemSelectorView
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2021-Apr-14.
 */
class BuildEditorView : View("Path of Taurewa") {
    private val model by inject<BuildEditorModel>()

    private var classComboBox: ComboBox<ClassTag> by singleAssign()
    private var ascendencyComboBox: ComboBox<Ascendency> by singleAssign()

    private val MAX_IMG_WIDTH = (Utils.UI_PREF_WIDTH - (10 * 6)) / 6
    private val PREF_IMG_WIDTH = (Utils.UI_PREF_WIDTH - (20 * 6)) / 6
    private val MIN_IMG_WIDTH = (Utils.UI_PREF_WIDTH - (30 * 6)) / 6


    override val root = borderpane {
        prefWidth = Utils.UI_PREF_WIDTH
        prefHeight = Utils.UI_PREF_HEIGHT
        paddingAll = 10.0
        top {
            paddingAll = 5.0
            //region Header Pane
            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                label(text = "Build Editor") {
                    addClass(Styles.title)
                }
                spacer { }
                vbox(spacing = 5.0, alignment = Pos.CENTER) {
                    paddingAll = 5.0
                    textfield(model.titleProperty) {
                        addClass(Styles.subtitle)
                    }
                    hbox(spacing = 5.0, alignment = Pos.CENTER) {
                        paddingAll = 5.0
                        spacer { }
                        textfield(model.versionProperty)
                        separator()
                        classComboBox = combobox(model.classProperty, values = ClassTag.values().toList()) {
                            cellFormat {
                                text = it.cleanName()
                            }
                        }
                        separator()
                        ascendencyComboBox =
                            combobox(model.ascendencyProperty, values = model.classTag.ascendencies.toList()) {
                                cellFormat {
                                    text = it.cleanName()
                                }
                            }
                        classComboBox.setOnAction {
                            ascendencyComboBox.items = (classComboBox.selectedItem?.ascendencies?.toList()
                                ?: emptyList()).asObservable()
                            ascendencyComboBox.selectionModel.select(0)
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
                                add(ItemEditorView(it).gridpaneConstraints {
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
                                add(ItemEditorView(it).gridpaneConstraints {
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
                                add(ItemEditorView(it).gridpaneConstraints {
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
                                add(ItemEditorView(it).gridpaneConstraints {
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
                                add(ItemEditorView(it).gridpaneConstraints {
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
        bottom {
            paddingAll = 5.0
            //region Footer Pane
            hbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                spacer { }
                button(text = "Save") {
                    addClass(Styles.sizedButton)
                    action {
                        val scope = Scope()
                        setInScope(BuildViewerModel(model.saveBuild()), scope)
                        find<BuildViewerView>(scope).openWindow(owner = null, resizable = false)
                        close()
                    }
                }
                spacer { }
            }
            //endregion
        }
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            val scope = Scope()
            setInScope(BuildViewerModel(model.originalBuild), scope)
            find<BuildViewerView>(scope).openWindow(owner = null, resizable = false)
            close()
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}