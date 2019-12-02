package macro.buddy.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import macro.buddy.config.Config;
import macro.buddy.data.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
	@FXML
	private ComboBox<String> classCombo;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		classCombo.getItems().setAll(Arrays.stream(Tags.ClassTag.values()).map(Enum::name).collect(Collectors.toList()));
		if (Config.CONFIG.getSettings().getClientFile() != null)
			Platform.runLater(() -> clientPath.setText(Config.CONFIG.getSettings().getClientFile()));
		if (Config.CONFIG.getSettings().getAccountName() != null)
			Platform.runLater(() -> accountName.setText(Config.CONFIG.getSettings().getAccountName()));
		if (Config.CONFIG.getSettings().getClassTag() != null)
			Platform.runLater(() -> classCombo.getSelectionModel().select(Config.CONFIG.getSettings().getClassName()));
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
		if (clientPath.getText() == null || clientPath.getText().trim().isEmpty()
				|| accountName == null || accountName.getText().trim().isEmpty()
				|| classCombo.getSelectionModel().getSelectedItem() == null || classCombo.getSelectionModel().getSelectedItem() == "UNKNOWN")
			return;
		Config.CONFIG.getSettings().setClientFile(clientPath.getText().trim());
		LOGGER.info("Client File has been updated to: " + clientPath.getText().trim());
		Config.CONFIG.getSettings().setAccountName(accountName.getText().trim());
		LOGGER.info("Account Name has been updated to: " + accountName.getText().trim());
		Config.CONFIG.getSettings().setClassName(classCombo.getSelectionModel().getSelectedItem());
		LOGGER.info("Class Name has been updated to: " + classCombo.getSelectionModel().getSelectedItem());
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