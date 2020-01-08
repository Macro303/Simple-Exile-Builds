package macro.buddy.builds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import macro.buddy.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private List<UpdateGem> updates;

	public BuildInfo() {
	}

	public BuildInfo(String name, ClassTag classTag, AscendencyTag ascendency, List<List<String>> links, List<UpdateGem> updates) {
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

	public List<UpdateGem> getUpdates() {
		return updates;
	}

	public void setUpdates(List<UpdateGem> updates) {
		this.updates = updates;
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

	public void save() {
		try {
			File buildFile = new File("builds", name.replaceAll(" ", "_") + ".yaml");
			Util.YAML_MAPPER.writeValue(buildFile, this);
		} catch (IOException ioe) {
			LOGGER.error("Unable to save build: " + ioe);
		}
	}
}