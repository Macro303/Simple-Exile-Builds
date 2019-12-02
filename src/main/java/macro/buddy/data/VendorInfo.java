package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02.
 */
public class VendorInfo {
	@NotNull
	private final String name;
	private final int act;
	@NotNull
	private final String npc;
	@NotNull
	private final List<Tags.ClassTag> classList;

	public VendorInfo(@NotNull String name, int act, @NotNull String npc, @NotNull List<Tags.ClassTag> classList) {
		this.name = name;
		this.act = act;
		this.npc = npc;
		this.classList = classList;
	}

	@NotNull
	public String getName() {
		return name;
	}

	public int getAct() {
		return act;
	}

	@NotNull
	public String getNpc() {
		return npc;
	}

	@NotNull
	public List<Tags.ClassTag> getClassList() {
		return classList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VendorInfo)) return false;

		VendorInfo that = (VendorInfo) o;

		if (act != that.act) return false;
		if (!name.equals(that.name)) return false;
		if (!npc.equals(that.npc)) return false;
		return classList.equals(that.classList);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + act;
		result = 31 * result + npc.hashCode();
		result = 31 * result + classList.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "VendorInfo{" +
				"name='" + name + '\'' +
				", act=" + act +
				", npc='" + npc + '\'' +
				", classList=" + classList +
				'}';
	}
}