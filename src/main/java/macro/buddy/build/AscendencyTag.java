package macro.buddy.build;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public enum AscendencyTag {
	NONE,
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

	public static SortedSet<AscendencyTag> values(ClassTag classTag) {
		switch (classTag) {
			case DUELIST:
				return new TreeSet<>(Arrays.asList(SLAYER, GLADIATOR, CHAMPION, NONE));
			case SHADOW:
				return new TreeSet<>(Arrays.asList(ASSASSIN, SABOTEUR, TRICKSTER, NONE));
			case MARAUDER:
				return new TreeSet<>(Arrays.asList(JUGGERNAUT, BERSERKER, CHIEFTAIN, NONE));
			case WITCH:
				return new TreeSet<>(Arrays.asList(NECROMANCER, ELEMENTALIST, OCCULTIST, NONE));
			case RANGER:
				return new TreeSet<>(Arrays.asList(DEADEYE, RAIDER, PATHFINDER, NONE));
			case TEMPLAR:
				return new TreeSet<>(Arrays.asList(INQUISTOR, HIEROPHANT, GUARDIAN, NONE));
			case SCION:
				return new TreeSet<>(Arrays.asList(ASCENDANT, NONE));
		}
		return new TreeSet<>();
	}
}