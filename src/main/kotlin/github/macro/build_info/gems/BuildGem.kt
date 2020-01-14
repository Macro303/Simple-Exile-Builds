package github.macro.build_info.gems

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13
 */
class BuildGem() : JsonModelAuto {
	val infoProperty = SimpleObjectProperty<GemInfo?>()
	var info by infoProperty

	val isVaalProperty = SimpleBooleanProperty()
	var isVaal by isVaalProperty

	val isAwakenedProperty = SimpleBooleanProperty()
	var isAwakened by isAwakenedProperty

	constructor(info: GemInfo?, isVaal: Boolean, isAwakened: Boolean) : this() {
		this.info = info
		this.isVaal = isVaal
		this.isAwakened = isAwakened
	}

	fun getFullname(): String? {
		return info?.let {
			var name = ""
			if (it.hasVaal && isVaal)
				name += "Vaal "
			if (it.hasAwakened && isAwakened)
				name += "Awakened "
			return@let name + it.name
		}
	}

	fun getFilename(): String? = info?.getFilename(isVaal, isAwakened)

	override fun toString(): String {
		return "BuildGem(infoProperty=$infoProperty, isVaalProperty=$isVaalProperty, isAwakenedProperty=$isAwakenedProperty)"
	}
}