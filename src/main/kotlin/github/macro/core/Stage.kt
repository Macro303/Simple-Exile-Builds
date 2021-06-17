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
        (0 until 6).forEach {
            if (this.weapons.size < it)
                this.weapons.add(Utils.getMissingGem().id)
            if (this.bodyArmour.size < it)
                this.bodyArmour.add(Utils.getMissingGem().id)
        }
        (0 until 4).forEach {
            if (this.helmet.size < it)
                this.helmet.add(Utils.getMissingGem().id)
            if (this.gloves.size < it)
                this.gloves.add(Utils.getMissingGem().id)
            if (this.boots.size < it)
                this.boots.add(Utils.getMissingGem().id)
        }
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