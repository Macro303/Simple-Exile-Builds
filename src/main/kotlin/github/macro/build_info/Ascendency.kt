package github.macro.build_info

import github.macro.build_info.ClassTag.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class Ascendency {
	SLAYER, GLADIATOR, CHAMPION, ASSASSIN, SABOTEUR, TRICKSTER, JUGGERNAUT, BERSERKER, CHIEFTAIN, NECROMANCER, ELEMENTALIST, OCCULTIST, DEADEYE, RAIDER, PATHFINDER, INQUISTOR, HIEROPHANT, GUARDIAN, ASCENDANT;

	companion object {
		fun values(classTag: ClassTag): List<Ascendency?> {
			return when (classTag) {
				DUELIST -> listOf(SLAYER, GLADIATOR, CHAMPION, null)
				SHADOW -> listOf(ASSASSIN, SABOTEUR, TRICKSTER, null)
				MARAUDER -> listOf(JUGGERNAUT, BERSERKER, CHIEFTAIN, null)
				WITCH -> listOf(NECROMANCER, ELEMENTALIST, OCCULTIST, null)
				RANGER -> listOf(DEADEYE, RAIDER, PATHFINDER, null)
				TEMPLAR -> listOf(INQUISTOR, HIEROPHANT, GUARDIAN, null)
				SCION -> listOf(ASCENDANT, null)
			}
		}
	}
}