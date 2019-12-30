package macro.buddy.gems;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.SortedSet;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo implements Comparable<GemInfo> {
	private String name;
	private String slot;
	private SortedSet<GemTag> tags;
	@JsonProperty("hasVaal")
	private boolean vaal;
	@JsonProperty("hasAwakened")
	private boolean awakened;

	public GemInfo() {
	}

	public GemInfo(String name, String slot, SortedSet<GemTag> tags, boolean vaal, boolean awakened) {
		this.name = name;
		this.slot = slot;
		this.tags = tags;
		this.vaal = vaal;
		this.awakened = awakened;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public SortedSet<GemTag> getTags() {
		return tags;
	}

	public void setTags(SortedSet<GemTag> tags) {
		this.tags = tags;
	}

	public boolean hasVaal() {
		return vaal;
	}

	public void setVaal(boolean vaal) {
		this.vaal = vaal;
	}

	public boolean hasAwakened() {
		return awakened;
	}

	public void setAwakened(boolean awakened) {
		this.awakened = awakened;
	}

	public String getFilename(boolean isVaal, boolean isAwakened) {
		String filename = getName().replaceAll(" ", "_");
		if (isVaal)
			filename += "[Vaal]";
		if (isAwakened)
			filename += "[Awakened]";
		return filename + ".png";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GemInfo)) return false;

		GemInfo gemInfo = (GemInfo) o;

		if (vaal != gemInfo.vaal) return false;
		if (awakened != gemInfo.awakened) return false;
		if (!name.equals(gemInfo.name)) return false;
		if (!slot.equals(gemInfo.slot)) return false;
		return tags.equals(gemInfo.tags);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + slot.hashCode();
		result = 31 * result + tags.hashCode();
		result = 31 * result + (vaal ? 1 : 0);
		result = 31 * result + (awakened ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", slot='" + slot + '\'' +
				", tags=" + tags +
				", vaal=" + vaal +
				", awakened=" + awakened +
				'}';
	}

	@Override
	public int compareTo(GemInfo other) {
		return getName().compareToIgnoreCase(other.getName());
	}
}