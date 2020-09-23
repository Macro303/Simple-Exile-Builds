package github.macro.ui.build_info

import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.core.build_info.Ascendency
import github.macro.core.build_info.ClassTag
import github.macro.ui.gems.GemEditorPane
import github.macro.ui.items.amulets.AmuletEditorPane
import github.macro.ui.items.body_armours.BodyArmourEditorPane
import github.macro.ui.items.belts.BeltEditorPane
import github.macro.ui.items.boots.BootsEditorPane
import github.macro.ui.items.flasks.FlaskEditorPane
import github.macro.ui.items.gloves.GlovesEditorPane
import github.macro.ui.items.helmets.HelmetEditorPane
import github.macro.ui.items.rings.RingEditorPane
import github.macro.ui.items.weapons.WeaponEditorPane
import javafx.geometry.HPos
import javafx.geometry.Insets
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
		prefWidth = Util.UI_PREF_WIDTH
		prefHeight = Util.UI_PREF_HEIGHT
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
								model.selectedBuild.buildGems.weapons.forEachIndexed { index, gem ->
									add(GemEditorPane(model.selectedBuild, gem, index, "Weapons")
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
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
								model.selectedBuild.buildGems.helmet.forEachIndexed { index, gem ->
									add(GemEditorPane(model.selectedBuild, gem, index, "Helmet")
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
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
								model.selectedBuild.buildGems.bodyArmour.forEachIndexed { index, gem ->
									add(GemEditorPane(model.selectedBuild, gem, index, "Body Armour")
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
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
								model.selectedBuild.buildGems.gloves.forEachIndexed { index, gem ->
									add(GemEditorPane(model.selectedBuild, gem, index, "Gloves")
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
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
								model.selectedBuild.buildGems.boots.forEachIndexed { index, gem ->
									add(GemEditorPane(model.selectedBuild, gem, index, "Boots")
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
							val ratio = 100.0 / 6.0
							constraintsForColumn(0).percentWidth = ratio
							constraintsForColumn(1).percentWidth = ratio
							constraintsForColumn(2).percentWidth = ratio
							constraintsForColumn(3).percentWidth = ratio
							constraintsForColumn(4).percentWidth = ratio
							constraintsForColumn(5).percentWidth = ratio
						}
					}
				}
				tab(text = "Items") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
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
								model.selectedBuild.buildItems.weapons.forEachIndexed { index, weapon ->
									add(WeaponEditorPane(model.selectedBuild, weapon, index)
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
							row {
								label("Armour") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 6
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								add(HelmetEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(BodyArmourEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(GlovesEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(BootsEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(BeltEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
							}
							row {
								label("Jewellery") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 6
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								add(AmuletEditorPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								separator {
									isVisible = false
								}
								model.selectedBuild.buildItems.rings.forEachIndexed { index, ring ->
									add(RingEditorPane(model.selectedBuild, ring, index)
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
							row {
								label("Flasks") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 6
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								model.selectedBuild.buildItems.flasks.forEachIndexed { index, flask ->
									add(FlaskEditorPane(model.selectedBuild, flask, index)
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
							val ratio = 100.0 / 5.0
							constraintsForColumn(0).percentWidth = ratio
							constraintsForColumn(1).percentWidth = ratio
							constraintsForColumn(2).percentWidth = ratio
							constraintsForColumn(3).percentWidth = ratio
							constraintsForColumn(4).percentWidth = ratio
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