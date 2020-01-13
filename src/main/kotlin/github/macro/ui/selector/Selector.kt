package github.macro.ui.selector

import github.macro.Util
import github.macro.build_info.BuildInfo
import github.macro.build_info.ClassTag
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
							LOGGER.info("Selecting Build: ${buildCombobox.selectedItem?.display()}")
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
					button(text = "Create") {
						minWidth = 100.0
						action {
							LOGGER.info("Creating Build: ${nameTextfield.text} | ${classCombobox.selectedItem}")
						}
						disableWhen {
							nameTextfield.textProperty().length().lessThanOrEqualTo(3)
								.or(classCombobox.valueProperty().isNull)
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