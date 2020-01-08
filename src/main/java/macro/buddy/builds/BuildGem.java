package macro.buddy.builds;

import macro.buddy.gems.GemInfo;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class BuildGem {
	private final GemInfo info;
	private final boolean isVaal;
	private final boolean isAwakened;

	public BuildGem(GemInfo info, boolean isVaal, boolean isAwakened) {
		this.info = info;
		this.isVaal = isVaal;
		this.isAwakened = isAwakened;
	}

	public GemInfo getInfo() {
		return info;
	}

	public boolean isVaal() {
		return isVaal;
	}

	public boolean isAwakened() {
		return isAwakened;
	}

	public String getFilename(){
		return info.getFilename(isVaal, isAwakened);
	}

	public String getFullname(){
		var name = "";
		if (info.hasVaal() && isVaal)
			name += "Vaal ";
		if (info.hasAwakened() && isAwakened)
			name += "Awakened ";
		return name + getInfo().getName();
	}
}