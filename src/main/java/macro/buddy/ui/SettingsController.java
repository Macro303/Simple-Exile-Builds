package macro.buddy.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.build.AscendencyTag;
import macro.buddy.build.BuildInfo;
import macro.buddy.Util;
import macro.buddy.build.BuildUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class SettingsController implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(SettingsController.class);
	@FXML
	private ComboBox<BuildInfo> buildCombo;
	@FXML
	private TextField buildName;
	@FXML
	private TextField pastebinCode;

	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCombo.getItems().setAll(BuildUtils.getBuilds());
		buildCombo.setConverter(new StringConverter<BuildInfo>() {
			@Override
			public String toString(BuildInfo object) {
				return object.getName() + " [" + object.getClassTag() + (object.getAscendency() == AscendencyTag.NONE ? "" : "/" + object.getAscendency().name()) + "]";
			}

			@Override
			public BuildInfo fromString(String string) {
				return null;
			}
		});
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void addBuild() {
		if (buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		/*BuildInfo.builds.add(BuildInfo.load(buildName.getText()));
		buildCombo.getItems().setAll(BuildInfo.builds);*/
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
		Optional<String> response = Util.stringRequest(URL);
		if (!response.isPresent())
			return;
		/*Optional<BuildInfo> build = ExileHelper.pasteBinPathOfBuilding(buildName.getText(), response.get());
		if (build.isPresent()) {
			BuildInfo.builds.add(build.get());
			buildCombo.getItems().setAll(BuildInfo.builds);
		}*/
	}

	public void saveAndContinue() {
		if (buildCombo.getSelectionModel().getSelectedItem() == null)
			return;

		try {
			stage.close();
			new GemsUI().start(stage);
		} catch (IOException ioe) {
			LOGGER.fatal("Unable to Load Buddy UI from Setup UI => " + ioe);
		}
	}
}