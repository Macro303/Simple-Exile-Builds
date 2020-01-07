package macro.buddy.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.Util;
import macro.buddy.builds.AscendencyTag;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;
import macro.buddy.builds.ClassTag;
import macro.buddy.gems.GemInfo;
import macro.buddy.gems.GemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
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
		build.getLinks().forEach(link ->{
			HBox linkBox = new HBox();
			linkBox.setSpacing(5.0);
			link.forEach(gemName -> {
				Optional<GemInfo> info = GemUtils.getGem(gemName);
				GemBox grid  = new GemBox(build, info, gemName);
				/*String borderStyle = String.format("-fx-border-color: %s; -fx-border-style: dashed; -fx-border-width: 2;", Util.slotToColour(info.isPresent() ? info.get().getSlot() : "Black"));

				GridPane grid = new GridPane();
				grid.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
				grid.setStyle(borderStyle);

				ImageView image = getImage(info, gemName);
				grid.add(image, 0, 0, 3, 1);
				GridPane.setHalignment(image, HPos.CENTER);

				Label name = new Label(info.isPresent() ? info.get().getName() : gemName);
				name.setWrapText(true);
				name.setPrefWidth(90);
				grid.add(name, 0, 1, 3, 1);
				GridPane.setVgrow(name, Priority.ALWAYS);

				Button previous = new Button("<<");
				grid.add(previous, 0, 2, 1, 1);

				Separator separator = new Separator(Orientation.HORIZONTAL);
				separator.setVisible(false);
				grid.add(separator, 1, 2, 1, 1);
				GridPane.setHgrow(separator, Priority.ALWAYS);

				Button next = new Button(">>");
				boolean hasNext = build.getUpdates().containsKey(gemName);
				next.setDisable(!hasNext);
				grid.add(next, 2, 2, 1, 1);*/

				linkBox.getChildren().add(grid);
			});
			buildGems.getChildren().add(linkBox);
		});
	}

	private ImageView getImage(Optional<GemInfo> gem, String gemName){
		String imageFile = getClass().getResource("placeholder[80x80].png").toExternalForm();
		if (gem.isPresent()) {
			String temp = String.format("gems\\%s", gem.get().getFilename(gemName.contains("Vaal"), gemName.contains("Awakened")));
			if (new File(temp).exists())
				imageFile = "file:" + temp;
		}
		ImageView image = new ImageView(new Image(imageFile));
		image.setFitHeight(80);
		image.setFitWidth(80);
		return image;
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