package macro.buddy.builds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Macro303 on 2019-Dec-02
 */
public class BuildInfo implements Comparable<BuildInfo> {
	@JsonIgnore
	private static final Logger LOGGER = LogManager.getLogger(BuildInfo.class);
	private String name;
	private ClassTag classTag;
	private AscendencyTag ascendency;
	private List<List<String>> links;
	private Map<String, Map<String, String>> updates;

	public BuildInfo() {
	}

	public BuildInfo(String name, ClassTag classTag, AscendencyTag ascendency, List<List<String>> links, Map<String, Map<String, String>> updates) {
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.links = links;
		this.updates = updates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("class")
	public ClassTag getClassTag() {
		return classTag;
	}

	public void setClassTag(ClassTag classTag) {
		this.classTag = classTag;
	}

	public AscendencyTag getAscendency() {
		return ascendency;
	}

	public void setAscendency(AscendencyTag ascendency) {
		this.ascendency = ascendency;
	}

	public List<List<String>> getLinks() {
		return links;
	}

	public void setLinks(List<List<String>> links) {
		this.links = links;
	}

	public Map<String, Map<String, String>> getUpdates() {
		return updates;
	}

	public void setUpdates(Map<String, Map<String, String>> updates) {
		this.updates = updates;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BuildInfo)) return false;

		BuildInfo info = (BuildInfo) o;

		if (!name.equals(info.name)) return false;
		if (classTag != info.classTag) return false;
		if (ascendency != info.ascendency) return false;
		if (!links.equals(info.links)) return false;
		return updates.equals(info.updates);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + classTag.hashCode();
		result = 31 * result + ascendency.hashCode();
		result = 31 * result + links.hashCode();
		result = 31 * result + updates.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "BuildInfo{" +
				"name='" + name + '\'' +
				", classTag=" + classTag +
				", ascendancy=" + ascendency +
				", links=" + links +
				", updates=" + updates +
				'}';
	}

	@Override
	public int compareTo(BuildInfo other) {
		return name.compareToIgnoreCase(other.name);
	}

	public void save(){
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
			mapper.findAndRegisterModules();
			mapper.registerModule(new Jdk8Module());
			File buildFile = new File("builds", name.replaceAll(" ", "_") + ".yaml");
			mapper.writeValue(buildFile, this);
		}catch (IOException ioe){
			LOGGER.error("Unable to save build: " + ioe);
		}
	}
}