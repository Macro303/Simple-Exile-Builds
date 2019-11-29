package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29
 */
public class SellerInfo {
	@NotNull
	private Tags.NPCTag name;
	private int act;
	@NotNull
	private Tags.TownTag town;
	@NotNull
	private List<Tags.ClassTag> available;
	@NotNull
	private String quest;

	public SellerInfo(@NotNull Tags.NPCTag name, int act, @NotNull Tags.TownTag town, @NotNull List<Tags.ClassTag> available, @NotNull String quest) {
		this.name = name;
		this.act = act;
		this.town = town;
		this.available = available;
		this.quest = quest;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SellerInfo)) return false;

		SellerInfo that = (SellerInfo) o;

		if (act != that.act) return false;
		if (!name.equals(that.name)) return false;
		if (!town.equals(that.town)) return false;
		if (!available.equals(that.available)) return false;
		return quest.equals(that.quest);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + act;
		result = 31 * result + town.hashCode();
		result = 31 * result + available.hashCode();
		result = 31 * result + quest.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "SellerInfo{" +
				"name='" + name + '\'' +
				", act=" + act +
				", town='" + town + '\'' +
				", available=" + available +
				", quest='" + quest + '\'' +
				'}';
	}
}