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
	public static final SortedSet<Build> builds = new TreeSet<>();
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
	@Nullable
	private String ascendency;
	@NotNull
	private List<List<String>> links;
	@NotNull
	private Map<String, Map<String, String>> updates;

	public Build() {
		this("INVALID");
	}

	public Build(@NotNull String buildName) {
		this(buildName, "Scion", null, new ArrayList<>(), new HashMap<>());
	}

	public Build(@NotNull String buildName, @NotNull String className, @Nullable String ascendency, @NotNull List<List<String>> links, @NotNull Map<String, Map<String, String>> updates) {
		this.buildName = buildName;
		this.className = className;
		this.ascendency = ascendency;
		this.links = links;
		this.updates = updates;
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
	public List<List<String>> getLinks() {
		return links;
	}

	public void setLinks(@NotNull List<List<String>> links) {
		this.links = links;
	}

	@NotNull
	public Map<String, Map<String, String>> getUpdates() {
		return updates;
	}

	public void setUpdates(@NotNull Map<String, Map<String, String>> updates) {
		this.updates = updates;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Build)) return false;

		Build build = (Build) o;

		if (!buildName.equals(build.buildName)) return false;
		if (!className.equals(build.className)) return false;
		if (!Objects.equals(ascendency, build.ascendency)) return false;
		if (!links.equals(build.links)) return false;
		return updates.equals(build.updates);
	}

	@Override
	public int hashCode() {
		int result = buildName.hashCode();
		result = 31 * result + className.hashCode();
		result = 31 * result + (ascendency != null ? ascendency.hashCode() : 0);
		result = 31 * result + links.hashCode();
		result = 31 * result + updates.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Build{" +
				"buildName='" + buildName + '\'' +
				", className='" + className + '\'' +
				", ascendency='" + ascendency + '\'' +
				", links=" + links +
				", updates=" + updates +
				'}';
	}

	@Override
	public int compareTo(@NotNull Build other) {
		return buildName.compareToIgnoreCase(other.buildName);
	}
}