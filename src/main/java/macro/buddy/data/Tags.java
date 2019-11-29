package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Tags {

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
		UNKNOWN;

		@NotNull
		public static GemTag fromName(@NotNull String name) {
			return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst().orElse(UNKNOWN);
		}
	}
}