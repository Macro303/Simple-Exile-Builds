package github.macro.ui.build_info

import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.ui.gems.GemViewerPane
import github.macro.ui.items.amulets.AmuletViewerPane
import github.macro.ui.items.body_armours.BodyArmourViewerPane
import github.macro.ui.items.belts.BeltViewerPane
import github.macro.ui.items.boots.BootsViewerPane
import github.macro.ui.items.flasks.FlaskViewerPane
import github.macro.ui.items.gloves.GlovesViewerPane
import github.macro.ui.items.helmets.HelmetViewerPane
import github.macro.ui.items.rings.RingViewerPane
import github.macro.ui.items.weapons.WeaponViewerPane
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildViewer : View("Exile Buddy") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	override val root = borderpane {
		prefWidth = Util.UI_PREF_WIDTH
		prefHeight = Util.UI_PREF_HEIGHT
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				label(text = model.selectedBuild.name) {
					addClass(Styles.title)
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					separator {
						isVisible = false
					}
					label(text = model.selectedBuild.version) {
						addClass(Styles.subtitle)
					}
					separator()
					label(text = "${model.selectedBuild.classTag.cleanName()}/${model.selectedBuild.ascendency.cleanName()}") {
						addClass(Styles.subtitle)
					}
					separator {
						isVisible = false
					}
					button(text = "Copy") {
						addClass(Styles.sizedButton)
						action {
							controller.copyBuild(oldView = this@BuildViewer)
						}
					}
					button(text = "Edit") {
						addClass(Styles.sizedButton)
						action {
							controller.editBuild(oldView = this@BuildViewer)
						}
					}
					button(text = "Delete") {
						addClass(Styles.sizedButton)
						action {
							controller.deleteBuild(oldView = this@BuildViewer)
						}
					}
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
									add(GemViewerPane(model.selectedBuild, gem, index, "Weapons")
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
									add(GemViewerPane(model.selectedBuild, gem, index, "Helmet")
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
									add(GemViewerPane(model.selectedBuild, gem, index, "Body Armour")
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
									add(GemViewerPane(model.selectedBuild, gem, index, "Gloves")
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
									add(GemViewerPane(model.selectedBuild, gem, index, "Boots")
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
									add(WeaponViewerPane(model.selectedBuild, weapon, index)
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
								add(HelmetViewerPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(BodyArmourViewerPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(GlovesViewerPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(BootsViewerPane(model.selectedBuild)
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
								add(BeltViewerPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								add(AmuletViewerPane(model.selectedBuild)
									.gridpaneConstraints {
										margin = Insets(2.5)
									})
								model.selectedBuild.buildItems.rings.forEachIndexed { index, ring ->
									add(RingViewerPane(model.selectedBuild, ring, index)
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
									add(FlaskViewerPane(model.selectedBuild, flask, index)
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
			controller.selectBuild(this@BuildViewer)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildViewer::class.java)
	}
}