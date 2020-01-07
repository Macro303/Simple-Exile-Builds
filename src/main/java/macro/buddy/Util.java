package macro.buddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	private static final Logger LOGGER = LogManager.getLogger(Util.class);

	public static String slotToColour(String slot) {
		switch (slot) {
			case "Red":
				return "#DD9999";
			case "Green":
				return "#99DD99";
			case "Blue":
				return "#9999DD";
			case "White":
				return "#DDDDDD";
			default:
				return "#999999";
		}
	}
}