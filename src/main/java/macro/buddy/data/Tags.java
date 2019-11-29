package macro.buddy.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Tags {
	private static final Logger LOGGER = LogManager.getLogger(Tags.class);

	public enum ClassTag {
		SCION,
		MARAUDER,
		RANGER,
		WITCH,
		DUELIST,
		TEMPLAR,
		SHADOW,
		UNKNOWN;

		@NotNull
		public static ClassTag fromId(int classId) {
			return Arrays.stream(values()).filter(tag -> tag.ordinal() == classId).findFirst().orElse(UNKNOWN);
		}

		@NotNull
		public static ClassTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}

	public enum ColourTag{
		RED,
		GREEN,
		BLUE,
		WHITE,
		UNKNOWN;

		@NotNull
		public static ColourTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}

	public enum GemTag{
		WARCRY,
		CHAOS,
		AOE,
		DURATION,
		ACTIVE,
		SUPPORT,
		COLD,
		LIGHTNING,
		FIRE,
		TRAP,
		ATTACK,
		TOTEM,
		MELEE,
		SPELL,
		AURA,
		MINION,
		CHAINING,
		PROJECTILE,
		BRAND,
		CURSE,
		TRIGGER,
		BOW,
		CHANNELLING,
		MOVEMENT,
		TRAVEL,
		BLINK,
		HERALD,
		GUARD,
		MINE,
		GOLEM,
		VAAL,
		PHYSICAL,
		STRIKE,
		NEW_GEMS,
		UNKNOWN;

		@NotNull
		public static GemTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}

	public enum NPCTag{
		PETARUS_AND_VANJA,
		LILLY_ROTH,
		NESSA,
		SIOSA,
		CLARISSA,
		YEENA,
		UNKNOWN;

		@NotNull
		public static NPCTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}

	public enum TownTag {
		HIGHGATE("Highgate"),
		LIONEYES_WATCH("Lioneye's Watch"),
		LIBRARY("Library"),
		THE_SARN_ENCAMPMENT("The Sarn Encampment"),
		THE_FOREST_ENCAMPMENT("The Forest Encampment"),
		UNKNOWN("Unknown");

		@NotNull
		private String display;

		TownTag(@NotNull String display){
			this.display = display;
		}

		@NotNull
		public static TownTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.display.equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}
}