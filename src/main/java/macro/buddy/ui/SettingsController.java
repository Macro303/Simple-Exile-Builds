package macro.buddy.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.Build;
import macro.buddy.Util;
import macro.buddy.data.ExileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class SettingsController implements Initializable {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(SettingsController.class);
	@FXML
	private ComboBox<Build> buildCombo;
	@FXML
	private TextField buildName;
	@FXML
	private TextField pastebinCode;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCombo.getItems().setAll(Build.builds);
		buildCombo.setConverter(new StringConverter<Build>() {
			@Override
			public String toString(Build object) {
				if (object.getAscendency() == null)
					return object.getBuildName() + "[" + object.getClassTag() + "]";
				else
					return object.getBuildName() + "[" + object.getClassTag() + "/" + object.getAscendency() + "]";
			}

			@Override
			public Build fromString(String string) {
				return null;
			}
		});
	}

	public void setStage(@NotNull Stage stage) {
		this.stage = stage;
	}

	public void addBuild() {
		if (buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		Build.builds.add(Build.load(buildName.getText()));
		buildCombo.getItems().setAll(Build.builds);
	}


	public void importBuild() {
		if (pastebinCode.getText() == null || pastebinCode.getText().trim().isEmpty()
				|| buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		String URL = "https://pastebin.com/raw/";
		if (pastebinCode.getText().startsWith("https://pastebin.com")) {
			try {
				URI uri = new URI(pastebinCode.getText());
				String[] segments = uri.getPath().split("/");
				URL += segments[segments.length - 1];
			} catch (URISyntaxException urise) {
				return;
			}
		} else
			URL += pastebinCode.getText();
		String response = Util.stringRequest(URL);
		if (response == null)
			return;
		Build build = ExileHelper.pasteBinPathOfBuilding(buildName.getText(), response);
		Build.builds.add(build);
		buildCombo.getItems().setAll(Build.builds);
	}

	public void saveAndContinue() {
		if (buildCombo.getSelectionModel().getSelectedItem() == null)
			return;
		Util.selectedBuild = buildCombo.getSelectionModel().getSelectedItem();

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