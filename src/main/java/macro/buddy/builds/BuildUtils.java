package macro.buddy.builds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import macro.buddy.Util;
import macro.buddy.gems.GemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public class BuildUtils {
	private static final Logger LOGGER = LogManager.getLogger(BuildUtils.class);
	private static SortedSet<BuildInfo> builds;

	static {
		loadBuilds();
	}

	public static void loadBuilds() {
		builds = new TreeSet<>();
		try {
			String[] buildFiles = new File("builds").list();
			for (String filename : buildFiles == null ? new String[]{} : buildFiles) {
				File buildFile = new File("builds", filename);
				BuildInfo info = Util.YAML_MAPPER.readValue(buildFile, BuildInfo.class);
				info.save();
				builds.add(info);
			}
		} catch (IOException ioe) {
			LOGGER.error("Unable to load Builds: " + ioe);
		}
	}

	public static Optional<BuildInfo> getBuild(String buildName) {
		return builds.stream().filter(build -> build.getName().equalsIgnoreCase(buildName)).findFirst();
	}

	public static SortedSet<BuildInfo> getBuilds() {
		return builds;
	}

	public static Optional<BuildGem> getGem(String name){
		var gem = GemUtils.getGem(name);
		return gem.map(gemInfo -> new BuildGem(gemInfo, name.startsWith("Vaal"), name.startsWith("Awakened")));
	}
}