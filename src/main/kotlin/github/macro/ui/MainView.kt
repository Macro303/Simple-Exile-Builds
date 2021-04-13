package github.macro.ui

import github.macro.Launcher
import github.macro.Styles
import github.macro.Utils.cleanName
import github.macro.core.Ascendency
import github.macro.core.BuildInfo
import github.macro.core.ClassTag
import github.macro.core.pantheon.MajorPantheon
import github.macro.core.pantheon.MinorPantheon
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.ListCell
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class MainView : View("Path of Taurewa") {
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
//						find<Settings>().openModal(block = true, resizable = false)
                        LOGGER.info("Opening Settings")
                    }
                }
                button(text = "\uD83D\uDCA1") {
                    action {
                        hostServices.showDocument("https://github.com/Macro303/Path-of-Taurewa")
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
                    val buildCombobox = combobox<BuildInfo> {
                        promptText = "Build"
                        hgrow = Priority.ALWAYS
                        converter = object : StringConverter<BuildInfo?>() {
                            override fun toString(build: BuildInfo?): String = build?.display ?: ""
                            override fun fromString(string: String): BuildInfo? = null
                        }
                    }
                    buildCombobox.converter
                    button(text = "Select") {
                        addClass(Styles.sizedButton)
                        action {
//							controller.viewBuild(oldView = this@MainView)
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
                                        name = nameTextField.text,
                                        classTag = classComboBox.selectedItem ?: ClassTag.SCION,
                                        ascendency = ascendencyComboBox.selectedItem ?: Ascendency.ASCENDANT,
                                        buildStages = emptyList(),
                                        bandits = emptyList(),
                                        major = MajorPantheon.ARAKAALI,
                                        minor = MinorPantheon.ABBERATH
                                    )
                                    build.save()
                                    val scope = Scope(BuildModel(build))
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