package macro.buddy;

import macro.buddy.config.Config;
import macro.buddy.data.ExileHelper;
import macro.buddy.ui.SettingsUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		Config.CONFIG = Config.load();
		String[] filenames = new File("builds").list();
		List<Build> builds = Arrays.stream(filenames == null ? new String[]{} : filenames).map(file -> file.replaceFirst("[.][^.]+$", "")).map(Build::load).collect(Collectors.toList());
		Build.builds.addAll(builds);
		new SettingsUI().init(args);
	}
}