package macro.buddy.gems;

import java.util.SortedSet;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo implements Comparable<GemInfo> {
	private String name;
	private String slot;
	private SortedSet<GemTag> tags;
	private boolean hasVaal;

	public GemInfo() {
	}

	public GemInfo(String name, String slot, SortedSet<GemTag> tags, boolean hasVaal) {
		this.name = name;
		this.slot = slot;
		this.tags = tags;
		this.hasVaal = hasVaal;
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
		return hasVaal;
	}

	public void setHasVaal(boolean hasVaal) {
		this.hasVaal = hasVaal;
	}

	public String getFilename(boolean isVaal) {
		String filename = getName().replaceAll(" ", "_");
		if (isVaal)
			filename += "[Vaal]";
		return filename + ".png";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GemInfo)) return false;

		GemInfo gemInfo = (GemInfo) o;

		if (hasVaal != gemInfo.hasVaal) return false;
		if (!name.equals(gemInfo.name)) return false;
		if (!slot.equals(gemInfo.slot)) return false;
		return tags.equals(gemInfo.tags);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + slot.hashCode();
		result = 31 * result + tags.hashCode();
		result = 31 * result + (hasVaal ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", slot='" + slot + '\'' +
				", tags=" + tags +
				", hasVaal=" + hasVaal +
				'}';
	}

	@Override
	public int compareTo(GemInfo other) {
		return getName().compareToIgnoreCase(other.getName());
	}
}