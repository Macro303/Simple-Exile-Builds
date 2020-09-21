package github.macro.ui

import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.ui.flasks.FlaskViewerPane
import github.macro.ui.gems.GemViewerPane
import github.macro.ui.rings.RingViewerPane
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
								model.selectedBuild.gems.weapons.forEachIndexed { index, gem ->
									add(GemViewerPane(model, gem)
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
								model.selectedBuild.gems.armour.forEach {
									add(GemViewerPane(model, it)
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
								model.selectedBuild.gems.helmet.forEach {
									add(GemViewerPane(model, it)
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
								model.selectedBuild.gems.gloves.forEach {
									add(GemViewerPane(model, it)
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
								model.selectedBuild.gems.boots.forEach {
									add(GemViewerPane(model, it)
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
				tab(text = "Gear") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						gridpane {
							row {
								label("Weapon/s") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								model.selectedBuild.gear.weapons.forEach {
									label(it.name).gridpaneConstraints {
										margin = Insets(2.5)
									}
								}
							}
							row {
								label("Armour") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.armour.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Helmet") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.helmet.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Gloves") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.gloves.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Boots") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.boots.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Belt") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.belt.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Amulet") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								label(model.selectedBuild.gear.amulet.name).gridpaneConstraints {
									margin = Insets(2.5)
								}
							}
							row {
								label("Rings") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								model.selectedBuild.gear.rings.forEach {
									add(RingViewerPane(model, it)
										.gridpaneConstraints {
											margin = Insets(2.5)
										})
								}
							}
							row {
								label("Flasks") {
									addClass(Styles.subtitle)
									gridpaneConstraints {
										columnSpan = 5
										hAlignment = HPos.CENTER
										margin = Insets(2.5)
									}
								}
							}
							row {
								model.selectedBuild.gear.flasks.forEach {
									add(FlaskViewerPane(model, it)
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