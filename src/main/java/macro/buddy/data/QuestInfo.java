package macro.buddy.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02.
 */
public class QuestInfo {
	@NotNull
	private final String name;
	private final int act;
	@NotNull
	private final List<Tags.ClassTag> classList;

	public QuestInfo(@NotNull String name, int act, @NotNull List<Tags.ClassTag> classList) {
		this.name = name;
		this.act = act;
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
	public List<Tags.ClassTag> getClassList() {
		return classList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof QuestInfo)) return false;

		QuestInfo questInfo = (QuestInfo) o;

		if (act != questInfo.act) return false;
		if (!name.equals(questInfo.name)) return false;
		return classList.equals(questInfo.classList);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + act;
		result = 31 * result + classList.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "QuestInfo{" +
				"name='" + name + '\'' +
				", act=" + act +
				", classList=" + classList +
				'}';
	}
}