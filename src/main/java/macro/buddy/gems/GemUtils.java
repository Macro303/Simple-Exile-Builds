package macro.buddy.gems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public class GemUtils {
	private static final Logger LOGGER = LogManager.getLogger(GemUtils.class);
	private static SortedSet<GemInfo> gems;

	static {
		gems = loadGems();
	}

	private static SortedSet<GemInfo> loadGems() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new Jdk8Module());
			return mapper.readValue(new File("gems", "Gems.json"), new TypeReference<SortedSet<GemInfo>>() {
			});
		} catch (IOException ioe) {
			LOGGER.error("Unable to load Gems: " + ioe);
		}
		return new TreeSet<>();
	}

	public static Optional<GemInfo> getGem(String gemName) {
		return gems.stream().filter(gem -> {
			if (gem.getName().equalsIgnoreCase(gemName))
				return true;
			else
				return gem.hasVaal() && ("Vaal " + gem.getName()).equalsIgnoreCase(gemName);
		}).findFirst();
	}
}