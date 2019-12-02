package macro.buddy.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class ExileHelper {
	private static final Logger LOGGER = LogManager.getLogger(ExileHelper.class);

	/*@NotNull
	public static List<CharacterInfo> getCharacters() {
		ArrayList<CharacterInfo> characters = new ArrayList<>();
		if (Config.CONFIG.getSettings().getAccountName() == null)
			return characters;
		String URL = "https://www.pathofexile.com/character-window/get-characters?accountName=" + Config.CONFIG.getSettings().getAccountName();
		JsonNode request = Util.jsonRequest(URL);
		if (request == null)
			return characters;
		for (Object obj : request.getArray()) {
			JSONObject charObj = (JSONObject) obj;
			CharacterInfo info = new CharacterInfo(
					charObj.getString("name"),
					charObj.getString("league"),
					Tags.ClassTag.fromId(charObj.getInt("classId")),
					charObj.getString("class"),
					charObj.getInt("level"),
					charObj.getInt("experience"),
					charObj.optBoolean("lastActive", false)
			);
			characters.add(info);
		}
		return characters;
	}*/

	@NotNull
	public static List<ActInfo> getGems() {
		ArrayList<ActInfo> acts = new ArrayList<>();
		GsonBuilder builder = new GsonBuilder().serializeNulls();
		builder.registerTypeAdapter(Tags.ClassTag.class, (JsonDeserializer<Tags.ClassTag>) (json, typeOfT, context) -> Tags.ClassTag.fromName(json.getAsString()));
		builder.registerTypeAdapter(Tags.GemTag.class, (JsonDeserializer<Tags.GemTag>) (json, typeOfT, context) -> Tags.GemTag.fromName(json.getAsString()));
		Gson gson = builder.create();
		try (Reader reader = new FileReader("Gems-v2.json")) {
			acts = gson.fromJson(reader, new TypeToken<List<ActInfo>>() {
			}.getType());
		} catch (IOException ioe) {
			LOGGER.error("Unable to load Gems: " + ioe);
		}
		return acts;
	}
}