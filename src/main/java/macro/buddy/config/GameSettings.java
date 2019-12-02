package macro.buddy.config;

import macro.buddy.data.Tags;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GameSettings {
	@Nullable
	private String clientFile;
	@Nullable
	private String accountName;
	@Nullable
	private String className;

	public GameSettings() {
		this(null, null, null);
	}

	public GameSettings(@Nullable String clientFile, @Nullable String accountName, @Nullable String className) {
		this.clientFile = clientFile;
		this.accountName = accountName;
		this.className = className;
	}

	public boolean isValid() {
		return clientFile != null && accountName != null && getClassTag() != null;
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

	@Nullable
	public Tags.ClassTag getClassTag() {
		if (className == null)
			return null;
		return Tags.ClassTag.fromName(className);
	}

	@Nullable
	public String getClassName() {
		return className;
	}

	public void setClassName(@Nullable String className) {
		this.className = className;
	}
}