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
			JsonArray array = new JsonParser().parse(new FileReader("gems/Gems.json")).getAsJsonArray();
			for (Object obj : array) {
				JsonObject gemObj = (JsonObject) obj;
				List<Tags.GemTag> gemTags = new ArrayList<>();
				for (JsonElement tagObj : gemObj.getAsJsonArray("gemTags")) {
					String tag = tagObj.getAsString();
					gemTags.add(Tags.GemTag.fromName(tag));
				}
				List<SellerInfo> sellers = new ArrayList<>();
				for (JsonElement element : gemObj.getAsJsonArray("buy")) {
					JsonObject sellerObj = element.getAsJsonObject();
					List<Tags.ClassTag> available = new ArrayList<>();
					for (JsonElement subElement : sellerObj.getAsJsonArray("available_to")) {
						Tags.ClassTag tag = Tags.ClassTag.fromName(subElement.getAsString());
						available.add(tag);
					}
					SellerInfo info = new SellerInfo(
							Tags.NPCTag.fromName(sellerObj.get("npc").getAsString()),
							sellerObj.get("act").getAsInt(),
							Tags.TownTag.fromName(sellerObj.get("town").getAsString()),
							available,
							sellerObj.get("quest_name").getAsString()
					);
					sellers.add(info);
				}
				GemInfo info = new GemInfo(
						gemObj.get("required_lvl").getAsInt(),
						Tags.ColourTag.fromName(gemObj.get("color").getAsString()),
						gemObj.get("isReward").getAsBoolean(),
						gemObj.get("isSupport").getAsBoolean(),
						sellers,
						gemObj.get("name").getAsString(),
						gemObj.get("isVaal").getAsBoolean(),
						gemTags,
						gemObj.get("isActive").getAsBoolean()
				);
				gems.add(info);
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error("Unable to load Gems: " + fnfe);
		}
		return gems;
	}
}