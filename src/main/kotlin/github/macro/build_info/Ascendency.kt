package github.macro.build_info

import github.macro.build_info.ClassTag.*

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
		fun value(name: String): Ascendency? = values().firstOrNull { it.name.equals(name, ignoreCase = true) }

		fun values(classTag: ClassTag): List<Ascendency> {
			return when (classTag) {
				DUELIST -> listOf(SLAYER, GLADIATOR, CHAMPION)
				SHADOW -> listOf(ASSASSIN, SABOTEUR, TRICKSTER)
				MARAUDER -> listOf(JUGGERNAUT, BERSERKER, CHIEFTAIN)
				WITCH -> listOf(NECROMANCER, ELEMENTALIST, OCCULTIST)
				RANGER -> listOf(DEADEYE, RAIDER, PATHFINDER)
				TEMPLAR -> listOf(INQUISTOR, HIEROPHANT, GUARDIAN)
				SCION -> listOf(ASCENDANT)
			}
		}
	}
}