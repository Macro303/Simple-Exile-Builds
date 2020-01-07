package macro.buddy.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.builds.AscendencyTag;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;
import macro.buddy.builds.ClassTag;
import macro.buddy.gems.GemInfo;
import macro.buddy.gems.GemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class GemsController implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(GemsController.class);
	@FXML
	private ComboBox<BuildInfo> buildCombo;
	@FXML
	private TextField buildName;
	@FXML
	private ComboBox<ClassTag> classCombo;
	@FXML
	private ComboBox<AscendencyTag> ascendencyCombo;
	@FXML
	private VBox buildGems;

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
		classCombo.getItems().setAll(ClassTag.values());
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setBuild(BuildInfo build) {
		buildGems.getChildren().clear();
		buildGems.setFillWidth(true);
		build.getLinks().forEach(link -> {
			HBox linkBox = new HBox();
			linkBox.setSpacing(5.0);
			link.forEach(gemName -> {
				Optional<GemInfo> info = GemUtils.getGem(gemName);
				GemBox grid = new GemBox(build, info, gemName);

				linkBox.getChildren().add(grid);
			});
			buildGems.getChildren().add(linkBox);
		});
	}

	public void addBuild() {
		if (buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		BuildInfo newInfo = new BuildInfo(buildName.getText(), classCombo.getValue(), ascendencyCombo.getValue(), new ArrayList<>(), new HashMap<>());
		newInfo.save();
		BuildUtils.loadBuilds();
		buildCombo.getItems().setAll(BuildUtils.getBuilds());
		buildCombo.getSelectionModel().select(newInfo);
	}

	public void buildSelection(ActionEvent event) {
		setBuild(buildCombo.getValue());
	}

	public void classSelection(ActionEvent event) {
		ascendencyCombo.setDisable(false);
		ascendencyCombo.getItems().setAll(AscendencyTag.values(classCombo.getValue()));
	}

	public void ascendencySelection(ActionEvent event) {
	}
}