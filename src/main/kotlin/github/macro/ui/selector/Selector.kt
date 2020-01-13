package github.macro.ui.selector

import github.macro.Util
import github.macro.build_info.Ascendency
import github.macro.build_info.BuildInfo
import github.macro.build_info.ClassTag
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
		prefWidth = 800.0
		prefHeight = 500.0
		paddingAll = 10.0
		top {
			hbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
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
			vbox(spacing = 5.0) {
				hbox(spacing = 5.0) {
					val buildCombobox = combobox<BuildInfo>(values = builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						maxWidth = Double.MAX_VALUE
						cellFormat {
							text = it.display()
						}
					}
					button(text = "Edit") {
						minWidth = 100.0
						action {
							LOGGER.info("Editing Build: ${buildCombobox.selectedItem?.display()}")
						}
						disableWhen {
							buildCombobox.valueProperty().isNull
						}
					}
					button(text = "Select") {
						minWidth = 100.0
						action {
							LOGGER.info("Viewing Build: ${buildCombobox.selectedItem?.display()}")
							val scope = Scope()
							setInScope(
								UIModel(SimpleObjectProperty<BuildInfo>(buildCombobox.selectedItem)),
								scope
							)
							replaceWith(find<Viewer>(scope))
						}
						disableWhen {
							buildCombobox.valueProperty().isNull
						}
					}
				}
				hbox(spacing = 5.0) {
					val nameTextfield = textfield {
						promptText = "Build Name"
						hgrow = Priority.ALWAYS
					}
					val classCombobox = combobox(values = ClassTag.values().asList()) {
						promptText = "Class"
					}
					val ascendancyCombobox = combobox(values = Ascendency.values().asList()) {
						promptText = "Ascendancy"
						disableWhen {
							classCombobox.valueProperty().isNull
						}
					}
					button(text = "Create") {
						minWidth = 100.0
						action {
							LOGGER.info("Creating Build: ${nameTextfield.text} | ${classCombobox.selectedItem} | ${ascendancyCombobox.selectedItem}")
						}
						disableWhen {
							nameTextfield.textProperty().length().lessThanOrEqualTo(3)
								.or(classCombobox.valueProperty().isNull)
//								.or(!Ascendency.values(classCombobox.selectedItem).contains(ascendancyCombobox.selectedItem))
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