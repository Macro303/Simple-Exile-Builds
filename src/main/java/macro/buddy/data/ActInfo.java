package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-03.
 */
public class ActInfo {
	private final int act;
	@NotNull
	private final List<QuestInfo> quests;
	@NotNull
	private final List<VendorInfo> vendors;

	public ActInfo(int act, @NotNull List<QuestInfo> quests, @NotNull List<VendorInfo> vendors) {
		this.act = act;
		this.quests = quests;
		this.vendors = vendors;
	}

	public int getAct() {
		return act;
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
		if (!(o instanceof ActInfo)) return false;

		ActInfo actInfo = (ActInfo) o;

		if (act != actInfo.act) return false;
		if (!quests.equals(actInfo.quests)) return false;
		return vendors.equals(actInfo.vendors);
	}

	@Override
	public int hashCode() {
		int result = act;
		result = 31 * result + quests.hashCode();
		result = 31 * result + vendors.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ActInfo{" +
				"act=" + act +
				", quests=" + quests +
				", vendors=" + vendors +
				'}';
	}
}