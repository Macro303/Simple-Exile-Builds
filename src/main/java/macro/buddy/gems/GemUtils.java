package macro.buddy.gems;

import com.fasterxml.jackson.core.type.TypeReference;
import macro.buddy.Util;
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
public class GemUtils {
	private static final Logger LOGGER = LogManager.getLogger(GemUtils.class);
	private static SortedSet<GemInfo> gems;

	static {
		gems = loadGems();
//		save();
	}

	private static SortedSet<GemInfo> loadGems() {
		try {
			return Util.JSON_MAPPER.readValue(new File("gems", "Gems.json"), new TypeReference<>() {
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
			else if (gem.hasVaal())
				return ("Vaal " + gem.getName()).equalsIgnoreCase(gemName);
			else if (gem.hasAwakened())
				return ("Awakened " + gem.getName()).equalsIgnoreCase(gemName);
			return false;
		}).findFirst();
	}

	public static void save() {
		try {
			Util.JSON_MAPPER.writeValue(new File("gems", "Gems.json"), gems);
		} catch (IOException ioe) {
			LOGGER.error("Unable to save Gems: " + ioe);
		}
	}
}