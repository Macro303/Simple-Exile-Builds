package github.buried_in_code.core

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class Ascendency {
    SLAYER,
    GLADIATOR,
    CHAMPION,
    ASSASSIN,
    SABOTEUR,
    TRICKSTER,
    JUGGERNAUT,
    BERSERKER,
    CHIEFTAIN,
    NECROMANCER,
    ELEMENTALIST,
    OCCULTIST,
    DEADEYE,
    RAIDER,
    PATHFINDER,
    INQUISTOR,
    HIEROPHANT,
    GUARDIAN,
    ASCENDANT;

    companion object {
        fun fromName(name: String?): Ascendency? = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        }
    }
}