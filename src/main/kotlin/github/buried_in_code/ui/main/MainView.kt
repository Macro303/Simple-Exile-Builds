package github.buried_in_code.ui.main

import github.buried_in_code.Launcher
import github.buried_in_code.Styles
import github.buried_in_code.Utils
import github.buried_in_code.Utils.cleanName
import github.buried_in_code.core.*
import github.buried_in_code.core.pantheon.MajorPantheon
import github.buried_in_code.core.pantheon.MinorPantheon
import github.buried_in_code.ui.build_viewer.BuildViewerModel
import github.buried_in_code.ui.build_viewer.BuildViewerView
import github.buried_in_code.ui.config.ConfigView
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.util.Duration
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Buried-In-Code on 2020-Jan-13.
 */
class MainView : View("Path of Taurewa") {
    private val model by inject<MainModel>()

    private var versionTextField: TextField by singleAssign()
    private var nameTextField: TextField by singleAssign()
    private var classComboBox: ComboBox<ClassTag> by singleAssign()
    private var ascendencyComboBox: ComboBox<Ascendency> by singleAssign()

    override val root = borderpane {
        prefWidth = 500.0
        prefHeight = 450.0
        paddingAll = 10.0
        top {
            paddingAll = 5.0
            hbox(spacing = 5.0, alignment = Pos.TOP_RIGHT) {
                button(text = "⚙") {
                    action {
                        find<ConfigView>().openModal(block = true, resizable = false)
                    }
                    tooltip("Config") {
                        showDelay = Duration(0.0)
                        hideDelay = Duration(0.0)
                    }
                }
                button(text = "\uD83D\uDCA1") {
                    action {
                        hostServices.showDocument("https://github.com/Buried-In-Code/Path-of-Taurewa")
                    }
                    tooltip("Support") {
                        showDelay = Duration(0.0)
                        hideDelay = Duration(0.0)
                    }
                }
            }
        }
        center {
            paddingAll = 5.0
            vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
                paddingAll = 5.0
                imageview(Launcher::class.java.getResource("logo.png")!!.toExternalForm(), lazyload = true) {
                    fitWidth = 320.0
                    fitHeight = 240.0
                }
                label(text = "Path of Taurewa") {
                    addClass(Styles.title)
                }
            }
        }
        bottom {
            paddingAll = 5.0
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                separator()
                hbox(spacing = 5.0, alignment = Pos.CENTER) {
                    paddingAll = 5.0
                    val buildCombobox = combobox<BuildInfo>(values = model.buildsProperty) {
                        promptText = "Build"
                        hgrow = Priority.ALWAYS
                        converter = object : StringConverter<BuildInfo>() {
                            override fun toString(build: BuildInfo): String = build.display
                            override fun fromString(string: String): BuildInfo? = null
                        }
                    }
                    button(text = "Select") {
                        addClass(Styles.sizedButton)
                        action {
                            val scope = Scope()
                            setInScope(BuildViewerModel(buildCombobox.selectedItem!!), scope)
                            find<BuildViewerView>(scope).openWindow(owner = null, resizable = false)
                            close()
                            LOGGER.info("Selecting Build")
                        }
                        disableWhen {
                            buildCombobox.valueProperty().isNull
                        }
                    }
                }
                separator()
                hbox(spacing = 5.0, alignment = Pos.CENTER) {
                    paddingAll = 5.0
                    vbox(spacing = 5.0, alignment = Pos.CENTER) {
                        paddingAll = 5.0
                        hbox(spacing = 5.0, alignment = Pos.CENTER) {
                            paddingAll = 5.0
                            versionTextField = textfield {
                                promptText = "PoE Version"
                            }
                            classComboBox = combobox(values = ClassTag.values().toList()) {
                                promptText = "Class"
                                cellFormat {
                                    text = it.cleanName()
                                }
                            }
                            ascendencyComboBox = combobox {
                                promptText = "Ascendency"
                                cellFormat {
                                    text = it.cleanName()
                                }
                                disableWhen {
                                    classComboBox.valueProperty().isNull
                                }
                            }
                            classComboBox.setOnAction {
                                ascendencyComboBox.items = (classComboBox.selectedItem?.ascendencies?.toList()
                                    ?: emptyList()).asObservable()
                                ascendencyComboBox.selectionModel.select(0)
                            }
                        }
                        hbox(spacing = 5.0, alignment = Pos.CENTER) {
                            paddingAll = 5.0
                            nameTextField = textfield {
                                promptText = "Build Name"
                                hgrow = Priority.ALWAYS
                            }
                            button(text = "Create") {
                                addClass(Styles.sizedButton)
                                action {
                                    val build = BuildInfo(
                                        version = versionTextField.text,
                                        title = nameTextField.text,
                                        classTag = classComboBox.selectedItem!!,
                                        ascendency = ascendencyComboBox.selectedItem!!,
                                        gemSlots = GemSlots(
                                            weapons = Utils.getStartingGems(classComboBox.selectedItem!!).map {
                                                GemEntry(it.id)
                                            }
                                        ),
                                        banditList = mutableListOf(),
                                        majorPantheon = MajorPantheon.ARAKAALI,
                                        minorPantheon = MinorPantheon.ABBERATH
                                    )
                                    build.save()
                                    val scope = Scope()
                                    setInScope(BuildViewerModel(build), scope)
                                    find<BuildViewerView>(scope).openWindow(owner = null, resizable = false)
                                    close()
                                }
                                disableWhen {
                                    versionTextField.textProperty().isEmpty
                                        .or(nameTextField.textProperty().length().lessThanOrEqualTo(3))
                                        .or(classComboBox.valueProperty().isNull)
                                        .or(ascendencyComboBox.valueProperty().isNull)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger(MainView::class.java)
    }
}