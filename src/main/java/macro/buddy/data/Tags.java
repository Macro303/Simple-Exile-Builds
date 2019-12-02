package macro.buddy.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
		SHADOW;

		@Nullable
		public static ClassTag fromId(int classId) {
			return Arrays.stream(values()).filter(tag -> tag.ordinal() == classId).findFirst().orElse(null);
		}

		@Nullable
		public static ClassTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst().orElse(null);
		}
	}

	public enum GemTag {
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
}