package macro.buddy;

import macro.buddy.data.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02
 */
public class Build {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(Build.class);
	@NotNull
	private static Yaml YAML;

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
	@NotNull
	private List<String> gemList;
	@NotNull
	private List<String> itemList;
	private boolean killBandits;

	public Build() {
		this("INVALID");
	}

	public Build(@NotNull String buildName) {
		this(buildName, "Scion", new ArrayList<>(), new ArrayList<>(), false);
	}

	public Build(@NotNull String buildName, @NotNull String className, @NotNull List<String> gemList, @NotNull List<String> itemList, boolean killBandits) {
		this.buildName = buildName;
		this.className = className;
		this.gemList = gemList;
		this.itemList = itemList;
		this.killBandits = killBandits;
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

	@NotNull
	public List<String> getGemList() {
		return gemList;
	}

	public void setGemList(@NotNull List<String> gemList) {
		this.gemList = gemList;
	}

	@NotNull
	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(@NotNull List<String> itemList) {
		this.itemList = itemList;
	}

	public boolean isKillBandits() {
		return killBandits;
	}

	public void setKillBandits(boolean killBandits) {
		this.killBandits = killBandits;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Build)) return false;

		Build build = (Build) o;

		if (killBandits != build.killBandits) return false;
		if (!buildName.equals(build.buildName)) return false;
		if (!className.equals(build.className)) return false;
		if (!gemList.equals(build.gemList)) return false;
		return itemList.equals(build.itemList);
	}

	@Override
	public int hashCode() {
		int result = buildName.hashCode();
		result = 31 * result + className.hashCode();
		result = 31 * result + gemList.hashCode();
		result = 31 * result + itemList.hashCode();
		result = 31 * result + (killBandits ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Build{" +
				"buildName='" + buildName + '\'' +
				", className='" + className + '\'' +
				", gemList=" + gemList +
				", itemList=" + itemList +
				", killBandits=" + killBandits +
				'}';
	}
}