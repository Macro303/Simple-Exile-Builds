package github.macro.ui.build_info

import github.macro.Launcher
import github.macro.Styles
import github.macro.Util.cleanName
import github.macro.core.build_info.Ascendency
import github.macro.core.build_info.Build
import github.macro.core.build_info.ClassTag
import github.macro.ui.settings.Settings
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildSelector : View("Simple Exile Builds") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

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
						find<Settings>().openModal(block = true, resizable = false)
					}
				}
				button(text = "\uD83D\uDCA1") {
					action {
						hostServices.showDocument("https://github.com/Macro303/Simple-Exile-Builds")
					}
				}
			}
		}
		center {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
				paddingAll = 5.0
				imageview(Launcher::class.java.getResource("logo.png").toExternalForm(), lazyload = true) {
					fitWidth = 320.0
					fitHeight = 240.0
				}
				label(text = "Simple Exile Builds") {
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
					val buildCombobox = combobox<Build>(property = model.selectedBuildProperty, values = model.builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						converter = object : StringConverter<Build?>() {
							override fun toString(build: Build?): String = build?.display ?: ""
							override fun fromString(string: String): Build? = null
						}
					}
					buildCombobox.converter
					button(text = "Select") {
						addClass(Styles.sizedButton)
						action {
							controller.viewBuild(oldView = this@BuildSelector)
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
							classComboBox = combobox(values = model.classes) {
								promptText = "Class"
								cellFormat {
									text = it.cleanName()
								}
							}
							ascendencyComboBox = combobox(values = model.ascendencies) {
								promptText = "Ascendency"
								cellFormat {
									text = it.cleanName()
								}
								disableWhen {
									classComboBox.valueProperty().isNull
								}
							}
							classComboBox.setOnAction {
								ascendencyComboBox.selectionModel.clearSelection()
								controller.updateClass(classComboBox.selectedItem!!)
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
									controller.createBuild(
										oldView = this@BuildSelector,
										version = versionTextField.text,
										name = nameTextField.text,
										classTag = classComboBox.selectedItem!!,
										ascendency = ascendencyComboBox.selectedItem!!
									)
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

	init {
		controller.loadBuilds()
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildSelector::class.java)
	}
}