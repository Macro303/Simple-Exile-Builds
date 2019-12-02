package macro.buddy.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import macro.buddy.Util;
import macro.buddy.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class ExileHelper {
	private static final Logger LOGGER = LogManager.getLogger(ExileHelper.class);

	@NotNull
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
	}

	@NotNull
	public static List<GemInfo> getGems() {
		ArrayList<GemInfo> gems = new ArrayList<>();
		try {
			JsonArray array = new JsonParser().parse(new FileReader("Gems.json")).getAsJsonArray();
			for (Object obj : array) {
				JsonObject gemObj = (JsonObject) obj;
				List<Tags.GemTag> tags = new ArrayList<>();
				for (JsonElement tagObj : gemObj.getAsJsonArray("tags")) {
					String tag = tagObj.getAsString();
					tags.add(Tags.GemTag.fromName(tag));
				}
				List<QuestInfo> quests = new ArrayList<>();
				for (JsonElement element : gemObj.getAsJsonArray("quests")) {
					JsonObject questObj = element.getAsJsonObject();
					List<Tags.ClassTag> classList = new ArrayList<>();
					for (JsonElement tagObj : questObj.getAsJsonArray("classes")) {
						String tag = tagObj.getAsString();
						classList.add(Tags.ClassTag.fromName(tag));
					}
					QuestInfo info = new QuestInfo(
							questObj.get("name").getAsString(),
							questObj.get("act").getAsInt(),
							classList
					);
					quests.add(info);
				}
				List<VendorInfo> vendors = new ArrayList<>();
				for (JsonElement element : gemObj.getAsJsonArray("vendors")) {
					JsonObject vendorObj = element.getAsJsonObject();
					List<Tags.ClassTag> classList = new ArrayList<>();
					for (JsonElement tagObj : vendorObj.getAsJsonArray("classes")) {
						String tag = tagObj.getAsString();
						classList.add(Tags.ClassTag.fromName(tag));
					}
					VendorInfo info = new VendorInfo(
							vendorObj.get("name").getAsString(),
							vendorObj.get("act").getAsInt(),
							vendorObj.get("npc").getAsString(),
							classList
					);
					vendors.add(info);
				}
				GemInfo info = new GemInfo(
						gemObj.get("colour").getAsString(),
						gemObj.get("name").getAsString(),
						tags,
						quests,
						vendors
				);
				gems.add(info);
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error("Unable to load Gems: " + fnfe);
		}
		return gems;
	}
}