package macro.buddy.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import macro.buddy.Util;
import macro.buddy.build.Build;
import macro.buddy.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
	private ComboBox<String> buildCombo;
	@FXML
	private TextField clientPath;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] filenames = new File("builds").list();
		String[] builds = Arrays.stream(filenames == null ? new String[]{} : filenames).map(file -> file.replaceFirst("[.][^.]+$", "")).collect(Collectors.toList()).toArray(new String[]{});
		buildCombo.getItems().setAll(builds);
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

	public void addBuild() {
		Build.load("temp");
		String[] filenames = new File("builds").list();
		String[] builds = Arrays.stream(filenames == null ? new String[]{} : filenames).map(file -> file.replaceFirst("[.][^.]+$", "")).collect(Collectors.toList()).toArray(new String[]{});
		buildCombo.getItems().setAll(builds);
	}

	public void saveAndContinue() {
		if (buildCombo.getSelectionModel().getSelectedItem() == null)
			return;
		Util.selectedBuild = Build.load(buildCombo.getSelectionModel().getSelectedItem());

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