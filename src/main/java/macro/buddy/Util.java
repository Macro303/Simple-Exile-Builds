package macro.buddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	private static final Logger LOGGER = LogManager.getLogger(Util.class);
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	public static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));

	static {
		JSON_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		JSON_MAPPER.findAndRegisterModules();
		JSON_MAPPER.registerModule(new Jdk8Module());
		YAML_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		YAML_MAPPER.findAndRegisterModules();
		YAML_MAPPER.registerModule(new Jdk8Module());
	}

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