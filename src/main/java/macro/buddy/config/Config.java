package macro.buddy.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Config {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(Config.class);
	@NotNull
	public static Config CONFIG;
	@NotNull
	private static Yaml YAML;

	static {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
		options.setPrettyFlow(true);
		YAML = new Yaml(options);
	}

	@NotNull
	private Connection proxy = new Connection();

	@NotNull
	public static Config load() {
		LOGGER.info("Loading Config");
		File temp = new File("config.yaml");
		if (!temp.exists())
			new Config().save();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("config.yaml"))) {
			return YAML.loadAs(br, Config.class).save();
		} catch (IOException ioe) {
			LOGGER.info("Unable to read File");
		}
		return new Config().save();
	}

	@NotNull
	public Connection getProxy() {
		return proxy;
	}

	public void setProxy(@NotNull Connection proxy) {
		this.proxy = proxy;
	}

	@NotNull
	public Config save() {
		LOGGER.info("Saving Config");
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("config.yaml"))) {
			bw.write(YAML.dumpAsMap(this));
		} catch (IOException ioe) {
			LOGGER.info("Unable to save File");
		}
		return this;
	}
}