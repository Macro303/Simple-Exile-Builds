package macro.buddy.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Config {
	@JsonIgnore
	private static final Logger LOGGER = LogManager.getLogger(Config.class);
	@JsonIgnore
	public static Config INSTANCE;

	private Connection proxy = new Connection();

	public static Config load() {
		LOGGER.info("Loading Config");
		File temp = new File("config.yaml");
		if (!temp.exists())
			new Config().save();
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
		mapper.findAndRegisterModules();
		mapper.registerModule(new Jdk8Module());
		try {
			return mapper.readValue(new File("config.yaml"), Config.class).save();
		} catch (IOException ioe) {
			LOGGER.error("Unable to read Config: {}", ioe.getLocalizedMessage());
		}
		return new Config().save();
	}

	public Connection getProxy() {
		return proxy;
	}

	public void setProxy(Connection proxy) {
		this.proxy = proxy;
	}

	public Config save() {
		LOGGER.info("Saving Config");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
		mapper.findAndRegisterModules();
		mapper.registerModule(new Jdk8Module());
		try {
			mapper.writeValue(new File("config.yaml"), this);
		} catch (IOException ioe) {
			LOGGER.error("Unable to write Config: {}", ioe.getLocalizedMessage());
		}
		return this;
	}
}