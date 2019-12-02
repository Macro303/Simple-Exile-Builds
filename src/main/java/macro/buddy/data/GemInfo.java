package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo {
	@NotNull
	private final String colour;
	@NotNull
	private final String name;
	@NotNull
	private final List<Tags.GemTag> tags;
	@NotNull
	private final List<QuestInfo> quests;
	@NotNull
	private final List<VendorInfo> vendors;

	public GemInfo(@NotNull String colour, @NotNull String name, @NotNull List<Tags.GemTag> tags, @NotNull List<QuestInfo> quests, @NotNull List<VendorInfo> vendors) {
		this.colour = colour;
		this.name = name;
		this.tags = tags;
		this.quests = quests;
		this.vendors = vendors;
	}

	@NotNull
	public String getColour() {
		return colour;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public List<Tags.GemTag> getTags() {
		return tags;
	}

	@NotNull
	public List<QuestInfo> getQuests() {
		return quests;
	}

	@NotNull
	public List<VendorInfo> getVendors() {
		return vendors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GemInfo)) return false;

		GemInfo info = (GemInfo) o;

		if (!colour.equals(info.colour)) return false;
		if (!name.equals(info.name)) return false;
		if (!tags.equals(info.tags)) return false;
		if (!quests.equals(info.quests)) return false;
		return vendors.equals(info.vendors);
	}

	@Override
	public int hashCode() {
		int result = colour.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + tags.hashCode();
		result = 31 * result + quests.hashCode();
		result = 31 * result + vendors.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"colour='" + colour + '\'' +
				", name='" + name + '\'' +
				", tags=" + tags +
				", quests=" + quests +
				", vendors=" + vendors +
				'}';
	}
}