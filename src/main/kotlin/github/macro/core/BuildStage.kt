package github.macro.core

import github.macro.Utils
import kotlinx.serialization.Serializable

/**
 * Created by Macro303 on 2021-Apr-12.
 */
@Serializable
data class BuildStage(
    val name: String,
    val weapons: List<String>,
    val helmet: List<String>,
    val bodyArmour: List<String>,
    val gloves: List<String>,
    val boots: List<String>
) {
    val weaponList: List<Gem>
        get() = weapons.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }
    val helmetList: List<Gem>
        get() = helmet.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }
    val bodyArmourList: List<Gem>
        get() = bodyArmour.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }
    val glovesList: List<Gem>
        get() = gloves.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }
    val bootsList: List<Gem>
        get() = boots.map { Gem.fromId(it) ?: Gem.fromName(it) ?: Utils.getMissingGem(it) }
}