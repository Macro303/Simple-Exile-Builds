package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo {
	private int requiredLevel;
	@NotNull
	private Tags.ColourTag colourName;
	private boolean isReward;
	private boolean isSupport;
	@NotNull
	private List<NPC> sellers;
	@NotNull
	private String name;
	private boolean isVaal;
	@NotNull
	private List<Tags.GemTag> gemTags;
	private boolean isActive;

	public GemInfo(int requiredLevel, @NotNull Tags.ColourTag colourName, boolean isReward, boolean isSupport, @NotNull List<NPC> sellers, @NotNull String name, boolean isVaal, @NotNull List<Tags.GemTag> gemTags, boolean isActive) {
		this.requiredLevel = requiredLevel;
		this.colourName = colourName;
		this.isReward = isReward;
		this.isSupport = isSupport;
		this.sellers = sellers;
		this.name = name;
		this.isVaal = isVaal;
		this.gemTags = gemTags;
		this.isActive = isActive;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GemInfo)) return false;

		GemInfo info = (GemInfo) o;

		if (requiredLevel != info.requiredLevel) return false;
		if (isReward != info.isReward) return false;
		if (isSupport != info.isSupport) return false;
		if (isVaal != info.isVaal) return false;
		if (isActive != info.isActive) return false;
		if (colourName != info.colourName) return false;
		if (!sellers.equals(info.sellers)) return false;
		if (!name.equals(info.name)) return false;
		return gemTags.equals(info.gemTags);
	}

	@Override
	public int hashCode() {
		int result = requiredLevel;
		result = 31 * result + colourName.hashCode();
		result = 31 * result + (isReward ? 1 : 0);
		result = 31 * result + (isSupport ? 1 : 0);
		result = 31 * result + sellers.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (isVaal ? 1 : 0);
		result = 31 * result + gemTags.hashCode();
		result = 31 * result + (isActive ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"requiredLevel=" + requiredLevel +
				", colourName=" + colourName +
				", isReward=" + isReward +
				", isSupport=" + isSupport +
				", sellers=" + sellers +
				", name='" + name + '\'' +
				", isVaal=" + isVaal +
				", gemTags=" + gemTags +
				", isActive=" + isActive +
				'}';
	}
}