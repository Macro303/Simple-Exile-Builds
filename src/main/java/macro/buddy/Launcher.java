package macro.buddy;

import macro.buddy.builds.*;
import macro.buddy.gems.GemInfo;
import macro.buddy.gems.GemUtils;
import macro.buddy.ui.GemsUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		BuildUtils.loadBuilds();
		new GemsUI().init(args);
	}
}