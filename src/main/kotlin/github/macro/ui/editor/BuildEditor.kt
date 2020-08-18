package github.macro.ui.editor

import github.macro.Styles
import github.macro.Util.cleanName
import github.macro.build_info.Ascendency
import github.macro.build_info.ClassTag
import github.macro.ui.UIController
import github.macro.ui.UIModel
import github.macro.ui.viewer.GemViewerPane
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildEditor : View("Exile Buddy") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	private var versionTextField by singleAssign<TextField>()
	private var classComboBox by singleAssign<ComboBox<ClassTag>>()
	private var ascendencyComboBox by singleAssign<ComboBox<Ascendency>>()
	private var nameTextField by singleAssign<TextField>()

	override val root = borderpane {
		prefWidth = 1000.0
		prefHeight = 750.0
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				versionTextField = textfield {
					promptText = "PoE Version"
					text = model.selectedBuild.version
				}
				classComboBox = combobox(values = model.classes) {
					promptText = "Class"
					cellFormat {
						text = it.cleanName()
					}
					selectionModel.select(model.selectedBuild.classTag)
				}
				model.selectedClass(model.selectedBuild.classTag)
				ascendencyComboBox = combobox(values = model.ascendencies) {
					promptText = "Ascendency"
					cellFormat {
						text = it.cleanName()
					}
					disableWhen {
						classComboBox.valueProperty().isNull
					}
					selectionModel.select(model.selectedBuild.ascendency)
				}
				classComboBox.setOnAction {
					ascendencyComboBox.selectionModel.clearSelection()
					controller.updateClass(classComboBox.selectedItem!!)
				}
				nameTextField = textfield {
					promptText = "Build Name"
					hgrow = Priority.ALWAYS
					text = model.selectedBuild.name
				}
				button(text = "Save") {
					addClass(Styles.sizedButton)
					action {
						controller.saveBuild(
							oldView = this@BuildEditor,
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
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
			}
		}
		center {
			paddingAll = 5.0
			tabpane {
				tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
				tab(text = "Gems") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							paddingAll = 5.0
							label("Weapons") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.gems.weapons.forEachIndexed { index, gem ->
									if (index == 6)
										return@forEachIndexed
									if (index == 3)
										add(separator())
									add(GemEditorPane(model, gem, index, "Weapons"))
								}
							}
							label("Armour") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.gems.armour.forEachIndexed { index, gem ->
									if (index == 6)
										return@forEachIndexed
									add(GemEditorPane(model, gem, index, "Armour"))
								}
							}
							label("Helmet") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.gems.helmet.forEachIndexed { index, gem ->
									if (index == 4)
										return@forEachIndexed
									add(GemEditorPane(model, gem, index, "Helmet"))
								}
							}
							label("Gloves") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.gems.gloves.forEachIndexed { index, gem ->
									if (index == 4)
										return@forEachIndexed
									add(GemEditorPane(model, gem, index, "Gloves"))
								}
							}
							label("Boots") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.gems.boots.forEachIndexed { index, gem ->
									if (index == 4)
										return@forEachIndexed
									add(GemEditorPane(model, gem, index, "Boots"))
								}
							}
						}
					}
				}
				tab(text = "Equipment") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							paddingAll = 5.0
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.weapons.forEach {
									label(it.name)
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.armour.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.helmet.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.gloves.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.boots.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.belt.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.amulet.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.rings.forEach {
									label(it.name)
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.flasks.forEach {
									label(it.name)
								}
							}
						}
					}
				}
			}
		}
	}

	override fun onDock() {
		currentWindow?.setOnCloseRequest {
			LOGGER.info("Closing Build: ${model.selectedBuild.display}")
			controller.viewBuild(this@BuildEditor)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildEditor::class.java)
	}
}