package github.macro.ui

import github.macro.core.item.BaseItem
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.ComboBox
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

/**
 * Created by Macro303 on 2020-Sep-28
 */
class AutoCompleteComboBoxListener<T : BaseItem>(private val comboBox: ComboBox<T>) : EventHandler<KeyEvent> {
	private val sb: StringBuilder = StringBuilder()
	private val data: ObservableList<T> = comboBox.items
	private var moveCaretToPos = false
	private var caretPos = 0

	override fun handle(event: KeyEvent) {
		when {
			event.code === KeyCode.UP -> {
				caretPos = -1
				moveCaret(comboBox.editor.text.length)
				return
			}
			event.code === KeyCode.DOWN -> {
				if (!comboBox.isShowing) {
					comboBox.show()
				}
				caretPos = -1
				moveCaret(comboBox.editor.text.length)
				return
			}
			event.code === KeyCode.BACK_SPACE -> {
				moveCaretToPos = true
				caretPos = comboBox.editor.caretPosition
			}
			event.code === KeyCode.DELETE -> {
				moveCaretToPos = true
				caretPos = comboBox.editor.caretPosition
			}
		}
		if (event.code === KeyCode.RIGHT || event.code === KeyCode.LEFT || event.isControlDown || event.code === KeyCode.HOME || event.code === KeyCode.END || event.code === KeyCode.TAB)
			return
		val list: ObservableList<T> = FXCollections.observableArrayList<T>()
		for (i in data.indices)
			if (data[i].getDisplayName().contains(comboBox.editor.text, ignoreCase = true))
				list.add(data[i])
		val t = comboBox.editor.text
		comboBox.items = list
		comboBox.editor.text = t
		if (!moveCaretToPos)
			caretPos = -1
		moveCaret(t.length)
		if (!list.isEmpty())
			comboBox.show()
	}

	private fun moveCaret(textLength: Int) {
		if (caretPos == -1)
			comboBox.editor.positionCaret(textLength)
		else
			comboBox.editor.positionCaret(caretPos)
		moveCaretToPos = false
	}

	init {
		this.comboBox.isEditable = true
		this.comboBox.onKeyPressed = EventHandler<KeyEvent?> { comboBox.hide() }
		this.comboBox.onKeyReleased = this@AutoCompleteComboBoxListener
	}
}