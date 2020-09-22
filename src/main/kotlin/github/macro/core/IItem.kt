package github.macro.core

import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
interface IItem {
	var id: String
	var name: String
	var isReleased: Boolean

	fun getDisplayName(): String

	fun getFile(): File
}