package macro.buddy.config;

import java.util.Optional;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GameSettings {
	private String clientFile;
	private String accountName;

	public GameSettings() {
		this(null, null);
	}

	public GameSettings(String clientFile, String accountName) {
		this.clientFile = clientFile;
		this.accountName = accountName;
	}

	public boolean isValid() {
		return clientFile != null && accountName != null;
	}

	public Optional<String> getClientFile() {
		return Optional.ofNullable(clientFile);
	}

	public void setClientFile(String clientFile) {
		this.clientFile = clientFile;
	}

	public Optional<String> getAccountName() {
		return Optional.ofNullable(accountName);
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}