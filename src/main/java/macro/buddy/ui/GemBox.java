package macro.buddy.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import macro.buddy.Util;
import macro.buddy.builds.BuildGem;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-07
 */
public class GemBox extends GridPane {
	private static final Logger LOGGER = LogManager.getLogger(GemBox.class);
	private BuildInfo build;
	private Optional<BuildGem> gem;

	public GemBox(BuildInfo build, Optional<BuildGem> gem) {
		this.build = build;
		this.gem = gem;
		initialize();
	}

	private void initialize() {
		getChildren().clear();

		setPadding(new Insets(5.0));
		setStyle(getBorderStyle());

		ImageView gemImage = getGemImage();
		add(gemImage, 0, 0, 3, 1);
		setHalignment(gemImage, HPos.CENTER);

		Label nameLabel = getNameLabel();
		add(nameLabel, 0, 1, 3, 1);
		setVgrow(nameLabel, Priority.ALWAYS);

		Button previousButton = getPreviousButton();
		if (previousButton.isVisible())
			add(previousButton, 0, 2);

		Button nextButton = getNextButton();
		if (nextButton.isVisible())
			add(nextButton, 2, 2);

		Separator separator = new Separator(Orientation.HORIZONTAL);
		separator.setVisible(false);
		setHgrow(separator, Priority.ALWAYS);
		if (previousButton.isVisible() || nextButton.isVisible())
			add(separator, 1, 2);
	}

	public String getBorderStyle() {
		return String.format("-fx-border-color: %s; -fx-border-style: dashed; -fx-border-width: 2;", Util.slotToColour(gem.isPresent() ? gem.get().getInfo().getSlot() : "Black"));
	}

	public ImageView getGemImage() {
		String imageFile = getClass().getResource("placeholder[80x80].png").toExternalForm();
		if (gem.isPresent()) {
			String temp = String.format("gems\\%s", gem.get().getFilename());
			if (new File(temp).exists())
				imageFile = "file:" + temp;
		}
		ImageView image = new ImageView(new Image(imageFile));
		image.setFitHeight(80);
		image.setFitWidth(80);
		return image;
	}

	public Label getNameLabel() {
		Label nameLabel = new Label(gem.isPresent() ? gem.get().getInfo().getName() : "Missing Gem");
		nameLabel.setWrapText(true);
		nameLabel.setPrefWidth(90);
		return nameLabel;
	}

	public Button getPreviousButton() {
		Button previous = new Button("<<");
		var previousGem = build.getUpdates().stream().filter(update -> {
			var newGem = BuildUtils.getGem(update.getNewGem());
			if (newGem.isEmpty())
				return false;
			return newGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem.get().getFullname() : "Missing Gem");
		}).map(update -> BuildUtils.getGem(update.getOldGem())).flatMap(Optional::stream).findFirst();
		previous.setVisible(previousGem.isPresent());
		previous.setOnAction(event -> {
			if (previousGem.isPresent()) {
				LOGGER.info("Previous Selected, Updating {} to {}", gem.isPresent() ? gem.get().getFullname() : "Missing Gem", previousGem.get().getFullname());
				gem = previousGem;
				initialize();
			}
		});
		return previous;
	}

	public Button getNextButton() {
		Button next = new Button(">>");
		var nextGem = build.getUpdates().stream().filter(update -> {
			var oldGem = BuildUtils.getGem(update.getOldGem());
			if (oldGem.isEmpty())
				return false;
			return oldGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem.get().getFullname() : "Missing Gem");
		}).map(update -> BuildUtils.getGem(update.getNewGem())).flatMap(Optional::stream).findFirst();
		next.setVisible(nextGem.isPresent());
		next.setOnAction(event -> {
			if (nextGem.isPresent()) {
				LOGGER.info("Next Selected, Updating {} to {}", gem.isPresent() ? gem.get().getFullname() : "Missing Gem", nextGem.get().getFullname());
				gem = nextGem;
				initialize();
			}
		});
		return next;
	}
}