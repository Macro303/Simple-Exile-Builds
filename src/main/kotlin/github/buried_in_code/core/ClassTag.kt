package github.buried_in_code.core

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class ClassTag(vararg val ascendencies: Ascendency) {
    SCION(Ascendency.ASCENDANT),
    MARAUDER(Ascendency.JUGGERNAUT, Ascendency.BERSERKER, Ascendency.CHIEFTAIN),
    RANGER(Ascendency.DEADEYE, Ascendency.RAIDER, Ascendency.PATHFINDER),
    WITCH(Ascendency.NECROMANCER, Ascendency.ELEMENTALIST, Ascendency.OCCULTIST),
    DUELIST(Ascendency.SLAYER, Ascendency.GLADIATOR, Ascendency.CHAMPION),
    TEMPLAR(Ascendency.INQUISTOR, Ascendency.HIEROPHANT, Ascendency.GUARDIAN),
    SHADOW(Ascendency.ASSASSIN, Ascendency.SABOTEUR, Ascendency.TRICKSTER);

    companion object {
        fun fromName(name: String?): ClassTag? = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        }
    }
}