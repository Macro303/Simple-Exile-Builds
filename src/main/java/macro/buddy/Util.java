package macro.buddy;

import javafx.scene.paint.Color;
import kong.unirest.*;
import macro.buddy.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	private static final Logger LOGGER = LogManager.getLogger(Util.class);
	@Nullable
	public static Build selectedBuild;

	static {
		Unirest.config().enableCookieManagement(false);
		if (Config.CONFIG.getProxy().getHostName() != null && Config.CONFIG.getProxy().getPort() != -1)
			Unirest.config().proxy(Config.CONFIG.getProxy().getHostName(), Config.CONFIG.getProxy().getPort());
	}

	@Nullable
	public static JsonNode jsonRequest(@NotNull String url) {
		GetRequest request = Unirest.get(url);
		request.header("Accept", "application/json");
		request.header("User-Agent", "Exile Buddy");
		LOGGER.debug("GET: " + request.getUrl() + " - " + request.getHeaders());
		HttpResponse<JsonNode> response;
		try {
			response = request.asJson();
		} catch (UnirestException ue) {
			LOGGER.error("Unable to load URL: " + ue);
			return null;
		}

		LOGGER.info("GET: " + response.getStatus() + " " + response.getStatusText() + " - " + request.getUrl());
		LOGGER.debug("Response: " + response.getBody());
		return response.getStatus() == 200 ? response.getBody() : null;
	}

	@Nullable
	public static Color strToColour(@NotNull String value) {
		switch (value) {
			case "Red":
				return Color.RED;
			case "Blue":
				return Color.BLUE;
			case "Green":
				return Color.GREEN;
			default:
				return null;
		}
	}
}