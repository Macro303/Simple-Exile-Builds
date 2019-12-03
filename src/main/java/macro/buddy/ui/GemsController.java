package macro.buddy.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.Build;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class GemsController implements Initializable {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(GemsController.class);
	@FXML
	private ComboBox<Build> buildCombo;
	@FXML
	private TextField buildName;
	@FXML
	private Button addBuild;
	@FXML
	private VBox gemVBox;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCombo.getItems().setAll(Build.builds);
		buildCombo.setConverter(new StringConverter<Build>() {
			@Override
			public String toString(Build object) {
				return object.getBuildName() + " [" + object.getClassTag() + (object.getAscendency() == null ? "" : "/" + object.getAscendency()) + "]";
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

	public void buildComboUpdate() {
		setBuild(buildCombo.getValue());
	}

	public void setBuild(@Nullable Build build) {
		gemVBox.getChildren().clear();
		if (build == null)
			return;
		for (String gem : build.getGemList()) {
			HBox box = new HBox();
			box.setSpacing(5.0);
			Label gemLabel = new Label(gem);
			/*Label gemLabel = new Label(gem.getName() + " [" + gem.getTags().stream().map(Enum::name).collect(Collectors.joining(", ")) + "]");
			if (Util.strToColour(gem.getColour()) != null)
				gemLabel.setStyle("-fx-text-fill: " + Util.strToColour(gem.getColour()));*/
			box.getChildren().addAll(gemLabel);
			gemVBox.getChildren().add(box);
		}
	}

	public void addBuild() {
		if (buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		Build newBuild = Build.load(buildName.getText());
		Build.builds.add(newBuild);
		buildCombo.getItems().setAll(Build.builds);
		buildCombo.getSelectionModel().select(newBuild);
	}
}