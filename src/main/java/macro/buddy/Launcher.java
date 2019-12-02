package macro.buddy;

import macro.buddy.config.Config;
import macro.buddy.ui.BuddyUI;
import macro.buddy.ui.SetupUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		Config.CONFIG = Config.load();
		new SetupUI().init(args);
	}
}