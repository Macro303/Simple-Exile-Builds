package macro.buddy.config;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GameSettings {
	@Nullable
	private String clientFile;
	@Nullable
	private String accountName;

	public GameSettings() {
		this(null, null);
	}

	public GameSettings(@Nullable String clientFile, @Nullable String accountName) {
		this.clientFile = clientFile;
		this.accountName = accountName;
	}

	public boolean isValid(){
		return clientFile != null && accountName != null;
	}

	@Nullable
	public String getClientFile() {
		return clientFile;
	}

	public void setClientFile(@Nullable String clientFile) {
		this.clientFile = clientFile;
	}

	@Nullable
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(@Nullable String accountName) {
		this.accountName = accountName;
	}
}