package macro.buddy;

import macro.buddy.data.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Macro303 on 2019-Dec-02
 */
public class Build implements Comparable<Build> {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(Build.class);
	@NotNull
	private static Yaml YAML;
	@NotNull
	public static final SortedSet<Build> builds = new TreeSet<>();

	static {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
		options.setPrettyFlow(true);
		YAML = new Yaml(options);
	}

	@NotNull
	private String buildName;
	@NotNull
	private String className;
	@Nullable
	private String ascendency;
	@NotNull
	private Map<String, List<String>> gemList;
	@NotNull
	private List<String> itemList;
	@Nullable
	private String helpBandit;

	public Build() {
		this("INVALID");
	}

	public Build(@NotNull String buildName) {
		this(buildName, "Scion", null, new HashMap<>(), new ArrayList<>(), null);
	}

	public Build(@NotNull String buildName, @NotNull String className, @Nullable String ascendency, @NotNull Map<String, List<String>> gemList, @NotNull List<String> itemList, @Nullable String helpBandit) {
		this.buildName = buildName;
		this.className = className;
		this.ascendency = ascendency;
		this.gemList = gemList;
		this.itemList = itemList;
		this.helpBandit = helpBandit;
	}

	@NotNull
	public static Build load(@NotNull String buildName) {
		LOGGER.info("Loading Build");
		new File("builds").mkdirs();
		File temp = new File("builds/" + buildName.replaceAll(" ", "_") + ".yaml");
		if (!temp.exists())
			new Build(buildName).save();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("builds/" + buildName.replaceAll(" ", "_") + ".yaml"))) {
			return YAML.loadAs(br, Build.class).save();
		} catch (IOException ioe) {
			LOGGER.info("Unable to read File");
		}
		return new Build(buildName).save();
	}

	@NotNull
	public Build save() {
		LOGGER.info("Saving Build");
		new File("builds").mkdirs();
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("builds/" + buildName.replaceAll(" ", "_") + ".yaml"))) {
			bw.write(YAML.dumpAsMap(this));
		} catch (IOException ioe) {
			LOGGER.info("Unable to save File");
		}
		return this;
	}

	@NotNull
	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(@NotNull String buildName) {
		this.buildName = buildName;
	}

	@NotNull
	public String getClassName() {
		return className;
	}

	public void setClassName(@NotNull String className) {
		this.className = className;
	}

	@NotNull
	public Tags.ClassTag getClassTag() {
		Tags.ClassTag classTag = Tags.ClassTag.fromName(className);
		return classTag == null ? Tags.ClassTag.SCION : classTag;
	}

	@Nullable
	public String getAscendency() {
		return ascendency;
	}

	public void setAscendency(@Nullable String ascendency) {
		this.ascendency = ascendency;
	}

	@NotNull
	public Map<String, List<String>> getGemList() {
		return gemList;
	}

	public void setGemList(@NotNull Map<String, List<String>> gemList) {
		this.gemList = gemList;
	}

	@NotNull
	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(@NotNull List<String> itemList) {
		this.itemList = itemList;
	}

	@Nullable
	public String getHelpBandit() {
		return helpBandit;
	}

	public void setHelpBandit(@Nullable String helpBandit) {
		this.helpBandit = helpBandit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Build)) return false;

		Build build = (Build) o;

		if (!buildName.equals(build.buildName)) return false;
		if (!className.equals(build.className)) return false;
		if (!Objects.equals(ascendency, build.ascendency)) return false;
		if (!gemList.equals(build.gemList)) return false;
		if (!itemList.equals(build.itemList)) return false;
		return Objects.equals(helpBandit, build.helpBandit);
	}

	@Override
	public int hashCode() {
		int result = buildName.hashCode();
		result = 31 * result + className.hashCode();
		result = 31 * result + (ascendency != null ? ascendency.hashCode() : 0);
		result = 31 * result + gemList.hashCode();
		result = 31 * result + itemList.hashCode();
		result = 31 * result + (helpBandit != null ? helpBandit.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Build{" +
				"buildName='" + buildName + '\'' +
				", className='" + className + '\'' +
				", ascendency='" + ascendency + '\'' +
				", gemList=" + gemList +
				", itemList=" + itemList +
				", helpBandit='" + helpBandit + '\'' +
				'}';
	}

	@Override
	public int compareTo(@NotNull Build other){
		return buildName.compareToIgnoreCase(other.buildName);
	}
}