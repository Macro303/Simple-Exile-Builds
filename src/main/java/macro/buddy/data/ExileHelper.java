package macro.buddy.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import macro.buddy.Build;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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
		try (Reader reader = new FileReader("Gems.json")) {
			acts = gson.fromJson(reader, new TypeToken<List<ActInfo>>() {
			}.getType());
		} catch (IOException ioe) {
			LOGGER.error("Unable to load Gems: " + ioe);
		}
		return acts;
	}

	@Nullable
	public static Build pasteBinPathOfBuilding(String buildName, String raw) {
		return null;
		/*String replace = raw.replace('-', '+').replace('_', '/').trim();

		byte[] byteValueBase64Decoded = null;
		try {
			byteValueBase64Decoded = Base64.getDecoder().decode(replace);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}

		String inflated = "";
		try {
			inflated = inflate(byteValueBase64Decoded);
		} catch (IOException | DataFormatException e) {
			System.out.println("Couldn't Inflate Path of Building Raw: " + raw);
		}

		LOGGER.debug("Inflated: " + inflated);
		Document doc = stringToXML(inflated);
		if (doc == null)
			return null;
		doc.getDocumentElement().normalize();

		//parse XML
		HashMap<String, List<String>> items = new HashMap<>();
		Node buildInfo = doc.getElementsByTagName("Build").item(0);
		Element info = (Element)buildInfo;
		String className = info.getAttribute("className");
		String ascendency = info.getAttribute("ascendClassName");
		String bandit = info.getAttribute("bandit");

		NodeList skillList = doc.getElementsByTagName("Skill");
		for(int index = 0; index < skillList.getLength(); index++){
			Element skill = (Element)skillList.item(index);
			String item = skill.getAttribute("label");
			if (item == null || item.trim().isEmpty())
				item = skill.getAttribute("slot");
			ArrayList<String> gems = new ArrayList<>();
			NodeList gemList = skill.getElementsByTagName("Gem");
			for(int gemIndex = 0; gemIndex < gemList.getLength(); gemIndex++){
				Element gemElement = (Element)gemList.item(gemIndex);
				String gem = gemElement.getAttribute("nameSpec");
				String gemId = gemElement.getAttribute("gemId");
				if (gemId != null && !gemId.trim().isEmpty())
					gems.add(gem);
			}
			if (gems.size() > 0)
				items.put(item, gems);
		}
		LOGGER.info("Items: " + items);
		return new Build(buildName, className, ascendency, items, new ArrayList<>(), bandit.equals("None") ? null : bandit).save();*/
	}

	private static String inflate(byte[] data) throws DataFormatException, IOException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		LOGGER.info("Original Length: " + data.length);
		LOGGER.info("Compressed Length: " + output.length);
		return new String(output);
	}

	private static Document stringToXML(String xmlString) {
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlString)));
		}catch (ParserConfigurationException | IOException | SAXException excep){
			LOGGER.warn("Unable to Parse String: " + excep);
		}
		return null;
	}
}