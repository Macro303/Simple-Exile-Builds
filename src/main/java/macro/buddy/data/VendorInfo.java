package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created by Macro303 on 2019-Dec-02.
 */
public class VendorInfo {
	@NotNull
	private final String name;
	@NotNull
	private final String npc;
	@NotNull
	private final Map<Tags.ClassTag, List<GemInfo>> rewards;

	public VendorInfo(@NotNull String name, @NotNull String npc, @NotNull Map<Tags.ClassTag, List<GemInfo>> rewards) {
		this.name = name;
		this.npc = npc;
		this.rewards = rewards;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public String getNpc() {
		return npc;
	}

	@NotNull
	public Map<Tags.ClassTag, List<GemInfo>> getRewards() {
		return rewards;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VendorInfo)) return false;

		VendorInfo that = (VendorInfo) o;

		if (!name.equals(that.name)) return false;
		if (!npc.equals(that.npc)) return false;
		return rewards.equals(that.rewards);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + npc.hashCode();
		result = 31 * result + rewards.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "VendorInfo{" +
				"name='" + name + '\'' +
				", npc='" + npc + '\'' +
				", rewards=" + rewards +
				'}';
	}
}