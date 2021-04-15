package github.macro.core

import github.macro.Utils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Macro303 on 2021-Apr-12.
 */
@Serializable
data class Stage(
    @SerialName("Name")
    var name: String,
    @SerialName("Weapon/s")
    var weapons: MutableList<String>,
    @SerialName("Helmet")
    var helmet: MutableList<String>,
    @SerialName("Body Armour")
    var bodyArmour: MutableList<String>,
    @SerialName("Gloves")
    var gloves: MutableList<String>,
    @SerialName("Boots")
    var boots: MutableList<String>
) {
    val weaponList: List<Gem>
        get() = weapons.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }.plus(TEMP).take(6)
    val helmetList: List<Gem>
        get() = helmet.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }.plus(TEMP).take(4)
    val bodyArmourList: List<Gem>
        get() = bodyArmour.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }.plus(TEMP).take(6)
    val glovesList: List<Gem>
        get() = gloves.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }.plus(TEMP).take(4)
    val bootsList: List<Gem>
        get() = boots.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }.plus(TEMP).take(4)

    init {
        this.weapons = weapons.take(6).toMutableList()
        this.helmet = helmet.take(4).toMutableList()
        this.bodyArmour = bodyArmour.take(6).toMutableList()
        this.gloves = gloves.take(4).toMutableList()
        this.boots = boots.take(4).toMutableList()
    }

    companion object {
        private val TEMP = listOf(
            Utils.getMissingGem(),
            Utils.getMissingGem(),
            Utils.getMissingGem(),
            Utils.getMissingGem(),
            Utils.getMissingGem(),
            Utils.getMissingGem()
        )
    }
}