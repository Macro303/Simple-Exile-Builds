package macro.buddy.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Duration;
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

		var gemImage = getGemImage();
		add(gemImage, 0, 0, 3, 1);
		setHalignment(gemImage, HPos.CENTER);

		var nameLabel = getNameLabel();
		add(nameLabel, 0, 1, 3, 1);
		setVgrow(nameLabel, Priority.ALWAYS);

		var previousButton = getPreviousButton();
		if (previousButton.isVisible())
			add(previousButton, 0, 2);

		var nextButton = getNextButton();
		if (nextButton.isVisible())
			add(nextButton, 2, 2);

		var separator = new Separator(Orientation.HORIZONTAL);
		separator.setVisible(false);
		setHgrow(separator, Priority.ALWAYS);
		if (previousButton.isVisible() || nextButton.isVisible())
			add(separator, 1, 2);
	}

	public String getBorderStyle() {
		return String.format("-fx-border-color: %s; -fx-border-style: dashed; -fx-border-width: 2;", Util.slotToColour(gem.isPresent() ? gem.get().getInfo().getSlot() : "Black"));
	}

	public ImageView getGemImage() {
		var imageFile = getClass().getResource("placeholder[80x80].png").toExternalForm();
		if (gem.isPresent()) {
			var temp = String.format("gems\\%s", gem.get().getFilename());
			if (new File(temp).exists())
				imageFile = "file:" + temp;
		}
		var image = new ImageView(new Image(imageFile));
		image.setFitHeight(80);
		image.setFitWidth(80);
		return image;
	}

	public Label getNameLabel() {
		var nameLabel = new Label(gem.isPresent() ? gem.get().getInfo().getName() : "Missing Gem");
		nameLabel.setWrapText(true);
		nameLabel.setPrefWidth(90);
		return nameLabel;
	}

	public Button getPreviousButton() {
		var previousButton = new Button("<<");
		var previousUpdate = build.getUpdates().stream().filter(update -> {
			var newGem = BuildUtils.getGem(update.getNewGem());
			if (newGem.isEmpty())
				return false;
			return newGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem.get().getFullname() : "Missing Gem");
		}).findFirst();
		previousButton.setVisible(previousUpdate.isPresent());
		if (previousUpdate.isEmpty())
			return previousButton;
		var previousGem = BuildUtils.getGem(previousUpdate.get().getOldGem());
		if (previousGem.isEmpty())
			LOGGER.warn("Missing Gem Entry for {}", previousUpdate.get().getOldGem());
		previousButton.setOnAction(event -> {
			if (previousGem.isPresent()) {
				LOGGER.info("Previous Selected, Updating {} to {}", gem.isPresent() ? gem.get().getFullname() : "Missing Gem", previousGem.get().getFullname());
				gem = previousGem;
				initialize();
			}
		});
		return previousButton;
	}

	public Button getNextButton() {
		var nextButton = new Button(">>");
		var nextUpdate = build.getUpdates().stream().filter(update -> {
			var oldGem = BuildUtils.getGem(update.getOldGem());
			if (oldGem.isEmpty())
				return false;
			return oldGem.get().getFullname().equalsIgnoreCase(gem.isPresent() ? gem.get().getFullname() : "Missing Gem");
		}).findFirst();
		nextButton.setVisible(nextUpdate.isPresent());
		if (nextUpdate.isEmpty())
			return nextButton;
		var nextGem = BuildUtils.getGem(nextUpdate.get().getNewGem());
		if (nextGem.isEmpty())
			LOGGER.warn("Missing Gem Entry for {}", nextUpdate.get().getNewGem());
		nextButton.setOnAction(event -> {
			if (nextGem.isPresent()) {
				LOGGER.info("Next Selected, Updating {} to {}", gem.isPresent() ? gem.get().getFullname() : "Missing Gem", nextGem.get().getFullname());
				gem = nextGem;
				initialize();
			}
		});
		if (nextButton.isVisible()){
			var tooltip = new Tooltip(nextUpdate.get().getReason());
			tooltip.setShowDelay(Duration.seconds(0));
			tooltip.setStyle("-fx-font-size: 10pt;");
			nextButton.setTooltip(tooltip);
		}
		return nextButton;
	}
}