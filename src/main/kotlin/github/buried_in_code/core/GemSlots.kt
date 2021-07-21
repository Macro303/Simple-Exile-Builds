package github.buried_in_code.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Buried-In-Code on 2021-Apr-12.
 */
@Serializable
data class GemSlots(
    @SerialName("Weapon/s")
    var weapons: List<GemEntry> = emptyList(),
    @SerialName("Helmet")
    var helmet: List<GemEntry> = emptyList(),
    @SerialName("Body Armour")
    var bodyArmour: List<GemEntry> = emptyList(),
    @SerialName("Gloves")
    var gloves: List<GemEntry> = emptyList(),
    @SerialName("Boots")
    var boots: List<GemEntry> = emptyList()
) {
    init {
        this.weapons = this.weapons.plus(TEMP).take(6)
        this.bodyArmour = this.bodyArmour.plus(TEMP).take(6)
        this.helmet = this.helmet.plus(TEMP).take(4)
        this.gloves = this.gloves.plus(TEMP).take(4)
        this.boots = this.boots.plus(TEMP).take(4)
    }

    companion object {
        private val TEMP = listOf(
            GemEntry("Missing"),
            GemEntry("Missing"),
            GemEntry("Missing"),
            GemEntry("Missing"),
            GemEntry("Missing"),
            GemEntry("Missing")
        )
    }
}