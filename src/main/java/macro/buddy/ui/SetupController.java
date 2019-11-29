package macro.buddy.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import macro.buddy.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class SetupController implements Initializable {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(SetupController.class);
	@FXML
	private TextField clientPath;
	@FXML
	private TextField accountName;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Config.CONFIG.getSettings().getClientFile() != null)
			Platform.runLater(() -> clientPath.setText(Config.CONFIG.getSettings().getClientFile()));
	}

	public void setStage(@NotNull Stage stage) {
		this.stage = stage;
	}

	public void searchFiles() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialFileName("Client.txt");
		chooser.setTitle("Select Path of Exile's Client.txt");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Client.txt", "Client.txt"));
		File selected = chooser.showOpenDialog(null);

		if (selected != null)
			Platform.runLater(() -> clientPath.setText(selected.getAbsolutePath()));
	}

	public void saveAndContinue() {
		if (clientPath.getText() == null || accountName == null)
			return;
		Config.CONFIG.getSettings().setClientFile(clientPath.getText());
		LOGGER.info("Client File has been updated to: " + clientPath.getText());
		Config.CONFIG.getSettings().setAccountName(accountName.getText());
		LOGGER.info("Account Name has been updated to: " + accountName.getText());
		Config.CONFIG.save();

		try {
			if (stage != null) {
				stage.close();
				new BuddyUI().start(stage);
			}
		} catch (IOException ioe) {
			LOGGER.fatal("Unable to Load Buddy UI from Setup UI => " + ioe);
		}
	}
}