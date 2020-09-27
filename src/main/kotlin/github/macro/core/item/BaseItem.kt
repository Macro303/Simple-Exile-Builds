package github.macro.core.item

import github.macro.core.Colour
import java.io.File

/**
 * Created by Macro303 on 2020-Sep-22
 */
abstract class BaseItem(
	var id: String,
	var name: String,
	var isReleased: Boolean,
	var colour: Colour = Colour.ERROR
) {
	abstract fun getDisplayName(): String

	fun getImageFile(): File {
		var image = File(File("resources", "Images"), id.substringAfterLast("/") + ".png")
		if (!image.exists())
			image = File(File("resources", "Images"), "placeholder.png")
		return image
	}
}