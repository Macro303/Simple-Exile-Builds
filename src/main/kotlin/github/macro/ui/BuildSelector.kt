package github.macro.ui

import github.macro.Launcher
import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.build_info.Ascendency
import github.macro.build_info.Build
import github.macro.build_info.BuildGems
import github.macro.build_info.ClassTag
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildSelector : View("Exile Buddy") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	private var versionTextField: TextField by singleAssign()
	private var nameTextField: TextField by singleAssign()
	private var classComboBox: ComboBox<ClassTag> by singleAssign()
	private var ascendencyComboBox: ComboBox<Ascendency> by singleAssign()

	override val root = borderpane {
		prefWidth = 500.0
		prefHeight = 550.0
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
				paddingAll = 5.0
				imageview(Launcher::class.java.getResource("logo.png").toExternalForm(), lazyload = true) {
					fitWidth = 320.0
					fitHeight = 240.0
				}
				label(text = "Exile Buddy") {
					addClass(Styles.title)
				}
			}
		}
		center {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				separator()
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					val buildCombobox = combobox<Build>(property = model.selectedBuildProperty, values = model.builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						cellFormat {
							text = it.display
						}
					}
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