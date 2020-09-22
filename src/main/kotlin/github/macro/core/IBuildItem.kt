package github.macro.core

/**
 * Created by Macro303 on 2020-Sep-22
 */
interface IBuildItem {
	var item: IItem
	var nextItem: IBuildItem?
	var reason: String?
}