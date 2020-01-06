package macro.buddy;

import macro.buddy.builds.BuildUtils;
import macro.buddy.config.Config;
import macro.buddy.ui.GemsUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		BuildUtils.loadBuilds();
		Config.INSTANCE = Config.load();
		/*ArrayList<String> sublink = new ArrayList<>();
		sublink.add("Caustic Arrow");
		sublink.add("Void Manipulation");
		sublink.add("Empower");
		ArrayList<String> sublink2 = new ArrayList<>();
		sublink2.add("Portal");
		ArrayList<List<String>> links = new ArrayList<>();
		links.add(sublink);
		links.add(sublink2);
		HashMap<String, String> updates = new HashMap<>();
		updates.put("Empower", "Enhance");
		Build test = new Build("Testing", "Ranger", "Raider", links, updates);
		test.save();*/
		new GemsUI().init(args);
	}
}