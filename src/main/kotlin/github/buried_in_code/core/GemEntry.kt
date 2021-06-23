package github.buried_in_code.core

import github.buried_in_code.Utils
import javafx.beans.property.SimpleObjectProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import tornadofx.getValue
import tornadofx.setValue

/*
 * Created by Buried-In-Code on 2021-Jun-17.
 */
@Serializable
data class GemEntry(
    @SerialName("Id")
    private var id: String,
    @SerialName("Next")
    var next: GemEntry? = null
) {
    @Transient
    val gemProperty = SimpleObjectProperty(Gem.fromId(id) ?: Gem.fromName(id) ?: Utils.getMissingGem(id))
    var gem by gemProperty
}