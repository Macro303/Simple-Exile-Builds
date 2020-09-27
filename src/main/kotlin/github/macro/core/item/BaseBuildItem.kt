package github.macro.core.item

/**
 * Created by Macro303 on 2020-Sep-22
 */
abstract class BaseBuildItem(
	var item: BaseItem,
	var nextItem: BaseBuildItem? = null,
	var reason: String? = null
)