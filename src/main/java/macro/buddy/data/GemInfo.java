package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo {
	@NotNull
	private final String name;
	@NotNull
	private final String colour;
	@NotNull
	private final List<Tags.GemTag> tags;

	public GemInfo(@NotNull String name, @NotNull String colour, @NotNull List<Tags.GemTag> tags) {
		this.name = name;
		this.colour = colour;
		this.tags = tags;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public String getColour() {
		return colour;
	}

	@NotNull
	public List<Tags.GemTag> getTags() {
		return tags;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GemInfo)) return false;

		GemInfo info = (GemInfo) o;

		if (!name.equals(info.name)) return false;
		if (!colour.equals(info.colour)) return false;
		return tags.equals(info.tags);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + colour.hashCode();
		result = 31 * result + tags.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", colour='" + colour + '\'' +
				", tags=" + tags +
				'}';
	}
}