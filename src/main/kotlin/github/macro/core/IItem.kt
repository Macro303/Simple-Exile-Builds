package github.macro.core

/**
 * Created by Macro303 on 2020-Sep-22
 */
interface IItem {
	var id: String
	var name: String
	var isReleased: Boolean

	fun getDisplayName(): String
}