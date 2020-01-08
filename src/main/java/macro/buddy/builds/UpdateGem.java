package macro.buddy.builds;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class UpdateGem {
	private String oldGem;
	private String newGem;
	private String reason;

	public UpdateGem() {
	}

	public UpdateGem(String oldGem, String newGem, String reason) {
		this.oldGem = oldGem;
		this.newGem = newGem;
		this.reason = reason;
	}

	public String getOldGem() {
		return oldGem;
	}

	public void setOldGem(String oldGem) {
		this.oldGem = oldGem;
	}

	public String getNewGem() {
		return newGem;
	}

	public void setNewGem(String newGem) {
		this.newGem = newGem;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}