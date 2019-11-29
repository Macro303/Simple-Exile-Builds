package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class CharacterInfo {
	@NotNull
	private final String name;
	@NotNull
	private final String league;
	@NotNull
	private final Tags.ClassTag className;
	@NotNull
	private final String ascendancyName;
	private final int level;
	private final int experience;
	private final boolean lastActive;

	public CharacterInfo(@NotNull String name, @NotNull String league, @NotNull Tags.ClassTag className, @NotNull String ascendancyName, int level, int experience, boolean lastActive) {
		this.name = name;
		this.league = league;
		this.className = className;
		this.ascendancyName = ascendancyName;
		this.level = level;
		this.experience = experience;
		this.lastActive = lastActive;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public String getLeague() {
		return league;
	}

	@NotNull
	public Tags.ClassTag getClassName() {
		return className;
	}

	@NotNull
	public String getAscendancyName() {
		return ascendancyName;
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public boolean isLastActive() {
		return lastActive;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CharacterInfo)) return false;

		CharacterInfo info = (CharacterInfo) o;

		if (level != info.level) return false;
		if (experience != info.experience) return false;
		if (lastActive != info.lastActive) return false;
		if (!name.equals(info.name)) return false;
		if (!league.equals(info.league)) return false;
		if (!className.equals(info.className)) return false;
		return ascendancyName.equals(info.ascendancyName);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + league.hashCode();
		result = 31 * result + className.hashCode();
		result = 31 * result + ascendancyName.hashCode();
		result = 31 * result + level;
		result = 31 * result + experience;
		result = 31 * result + (lastActive ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CharacterInfo{" +
				"name='" + name + '\'' +
				", league='" + league + '\'' +
				", className='" + className + '\'' +
				", ascendancyName='" + ascendancyName + '\'' +
				", level=" + level +
				", experience=" + experience +
				", lastActive=" + lastActive +
				'}';
	}
}