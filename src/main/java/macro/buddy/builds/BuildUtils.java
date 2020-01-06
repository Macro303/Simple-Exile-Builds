package macro.buddy.builds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
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
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
			mapper.findAndRegisterModules();
			mapper.registerModule(new Jdk8Module());
			String[] buildFiles = new File("builds").list();
			for (String filename : buildFiles == null ? new String[]{} : buildFiles) {
				File buildFile = new File("builds", filename);
				BuildInfo info = mapper.readValue(buildFile, BuildInfo.class);
				mapper.writeValue(buildFile, info);
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
}