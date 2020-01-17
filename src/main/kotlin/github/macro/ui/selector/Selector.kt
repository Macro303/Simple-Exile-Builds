package github.macro.ui.selector

import github.macro.Util
import github.macro.build_info.Ascendency
import github.macro.build_info.BuildInfo
import github.macro.build_info.ClassTag
import github.macro.build_info.gems.UpdateGem
import github.macro.ui.UIModel
import github.macro.ui.viewer.Viewer
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Selector : View() {
	private val builds = FXCollections.observableArrayList<BuildInfo>()
	private val ascendencyList = FXCollections.observableArrayList<Ascendency>()

	init {
		File("builds").listFiles().forEach {
			try {
				builds.add(Util.YAML_MAPPER.readValue(it, BuildInfo::class.java))
			} catch (ioe: IOException) {
				LOGGER.error("Unable to Load Build: ${it.nameWithoutExtension} | $ioe")
			}
		}
	}

	override val root = borderpane {
		paddingAll = 10.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
			}
		}
		center {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					val buildCombobox = combobox<BuildInfo>(values = builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						maxWidth = Double.MAX_VALUE
						cellFormat {
							text = it.display()
						}
					}
					button(text = "Select") {
						minWidth = 100.0
						action {
							LOGGER.info("Viewing Build: ${buildCombobox.selectedItem?.display()}")
							val scope = Scope()
							setInScope(UIModel(SimpleObjectProperty(buildCombobox.selectedItem)), scope)
							find<Viewer>(scope).openWindow(owner = null, resizable = false)
							close()
						}
						disableWhen {
							buildCombobox.valueProperty().isNull
						}
					}
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					val nameTextfield = textfield {
						promptText = "Build Name"
						hgrow = Priority.ALWAYS
					}
					val classCombobox = combobox(values = ClassTag.values().asList()) {
						promptText = "Class"
					}
					val ascendencyCombobox = combobox(values = ascendencyList) {
						promptText = "Ascendency"
						disableWhen {
							classCombobox.valueProperty().isNull
						}
					}
					classCombobox.setOnAction {
						ascendencyCombobox.selectionModel.clearSelection()
						if (classCombobox.value != null)
							ascendencyList.setAll(Ascendency.values(classCombobox.value))
					}
					button(text = "Create") {
						minWidth = 100.0
						action {
							val info = BuildInfo(
								name = nameTextfield.text,
								classTag = classCombobox.selectedItem!!,
								ascendency = ascendencyCombobox.selectedItem!!,
								links = listOf(
									Util.getClassGems(classTag = classCombobox.selectedItem!!),
									listOf(Util.gemByName(name = "Portal"))
								),
								updates = listOf(
									UpdateGem(
										oldGem = Util.gemByName("Empower Support"),
										newGem = Util.gemByName("Enhance Support"),
										reason = "Gems are all Max Level"
									)
								),
								equipment = emptyList()
							)
							LOGGER.info("Creating Build: ${info.display()}")
							info.save()
							val scope = Scope()
							setInScope(UIModel(SimpleObjectProperty(info)), scope)
							find<Viewer>(scope).openWindow(owner = null, resizable = false)
							close()
						}
						disableWhen {
							nameTextfield.textProperty().length().lessThanOrEqualTo(3)
								.or(classCombobox.valueProperty().isNull)
								.or(ascendencyCombobox.valueProperty().isNull)
						}
					}
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Selector::class.java)
	}
}