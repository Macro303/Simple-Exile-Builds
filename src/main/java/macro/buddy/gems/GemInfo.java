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
	private boolean hasAwakened;

	public GemInfo() {
	}

	public GemInfo(String name, String slot, SortedSet<GemTag> tags, boolean hasVaal, boolean hasAwakened) {
		this.name = name;
		this.slot = slot;
		this.tags = tags;
		this.hasVaal = hasVaal;
		this.hasAwakened = hasAwakened;
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

	public boolean hasAwakened() {
		return hasAwakened;
	}

	public void setHasAwakened(boolean hasAwakened) {
		this.hasAwakened = hasAwakened;
	}

	public String getFilename(boolean isVaal, boolean isAwakened) {
		String filename = getName().replaceAll(" ", "_");
		if (isVaal && hasVaal)
			filename += "[Vaal]";
		if (isAwakened && hasAwakened)
			filename += "[Awakened]";
		return filename + ".png";
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", slot='" + slot + '\'' +
				", tags=" + tags +
				", hasVaal=" + hasVaal +
				", hasAwakened=" + hasAwakened +
				'}';
	}

	@Override
	public int compareTo(GemInfo other) {
		return getName().compareToIgnoreCase(other.getName());
	}
}