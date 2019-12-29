package macro.buddy;

import kong.unirest.*;
import macro.buddy.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	private static final Logger LOGGER = LogManager.getLogger(Util.class);

	static {
		Unirest.config().enableCookieManagement(false);
		if (Config.INSTANCE.getProxy().getHostName().isPresent() && Config.INSTANCE.getProxy().getPort().isPresent())
			Unirest.config().proxy(Config.INSTANCE.getProxy().getHostName().get(), Config.INSTANCE.getProxy().getPort().get());
		Unirest.config().setObjectMapper(new JacksonObjectMapper());
	}

	public static Optional<JsonNode> jsonRequest(String url) {
		GetRequest request = Unirest.get(url);
		request.header("Accept", "application/json");
		request.header("User-Agent", "Exile Buddy");
		LOGGER.debug("GET: " + request.getUrl() + " - " + request.getHeaders());
		HttpResponse<JsonNode> response;
		try {
			response = request.asJson();
		} catch (UnirestException ue) {
			LOGGER.error("Unable to load URL: " + ue);
			return Optional.empty();
		}

		LOGGER.info("GET: " + response.getStatus() + " " + response.getStatusText() + " - " + request.getUrl());
		LOGGER.debug("Response: " + response.getBody());
		return response.getStatus() == 200 ? Optional.ofNullable(response.getBody()) : Optional.empty();
	}

	public static Optional<String> stringRequest(String url) {
		GetRequest request = Unirest.get(url);
		request.header("Accept", "application/json");
		request.header("User-Agent", "Exile Buddy");
		LOGGER.debug("GET: " + request.getUrl() + " - " + request.getHeaders());
		HttpResponse<String> response;
		try {
			response = request.asString();
		} catch (UnirestException ue) {
			LOGGER.error("Unable to load URL: " + ue);
			return Optional.empty();
		}

		LOGGER.info("GET: " + response.getStatus() + " " + response.getStatusText() + " - " + request.getUrl());
		LOGGER.debug("Response: " + response.getBody());
		return response.getStatus() == 200 ? Optional.ofNullable(response.getBody()) : Optional.empty();
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