package github.macro.core.item.gear

import github.macro.core.Colour
import github.macro.core.item.BaseItem

/**
 * Created by Macro303 on 2020-Jan-16.
 */
abstract class BaseGear(
	id: String,
	name: String,
	isReleased: Boolean,
	var isUnique: Boolean
) : BaseItem(
	id = id,
	name = name,
	isReleased = isReleased,
	colour = if (id == "None" || id == "Missing") Colour.ERROR else if (isUnique) Colour.ORANGE else Colour.YELLOW
) {
	override fun getDisplayName(): String = name
}