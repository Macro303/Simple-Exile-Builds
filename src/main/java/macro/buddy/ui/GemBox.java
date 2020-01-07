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
import macro.buddy.builds.BuildInfo;
import macro.buddy.gems.GemInfo;
import macro.buddy.gems.GemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Macro303 on 2020-Jan-07
 */
public class GemBox extends GridPane {
	private static final Logger LOGGER = LogManager.getLogger(GemBox.class);
	private BuildInfo build;
	private Optional<GemInfo> gem;
	private String fullName;

	public GemBox(BuildInfo build, Optional<GemInfo> gem, String fullName) {
		this.build = build;
		this.gem = gem;
		this.fullName = fullName;
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
		add(previousButton, 0, 2);

		Separator separator = new Separator(Orientation.HORIZONTAL);
		separator.setVisible(false);
		add(separator, 1, 2);
		setHgrow(separator, Priority.ALWAYS);

		Button nextButton = getNextButton();
		add(nextButton, 2, 2);
	}

	public String getBorderStyle() {
		return String.format("-fx-border-color: %s; -fx-border-style: dashed; -fx-border-width: 2;", Util.slotToColour(gem.isPresent() ? gem.get().getSlot() : "Black"));
	}

	public ImageView getGemImage() {
		String imageFile = getClass().getResource("placeholder[80x80].png").toExternalForm();
		if (gem.isPresent()) {
			String temp = String.format("gems\\%s", gem.get().getFilename(fullName.contains("Vaal"), fullName.contains("Awakened")));
			if (new File(temp).exists())
				imageFile = "file:" + temp;
		}
		ImageView image = new ImageView(new Image(imageFile));
		image.setFitHeight(80);
		image.setFitWidth(80);
		return image;
	}

	public Label getNameLabel() {
		Label nameLabel = new Label(gem.isPresent() ? gem.get().getName() : fullName);
		nameLabel.setWrapText(true);
		nameLabel.setPrefWidth(90);
		return nameLabel;
	}

	public Button getPreviousButton() {
		Button previous = new Button("<<");
		boolean valid = build.getUpdates().values().stream().anyMatch(entry -> entry.get("gem").equalsIgnoreCase(fullName));
		previous.setVisible(valid);
		previous.setOnAction(event -> {
			LOGGER.info("Previous Selected, Updating {}", gem.isPresent() ? gem.get().getName() : fullName);
			AtomicReference<String> previousGem = new AtomicReference<>();
			build.getUpdates().keySet().forEach(temp ->{
				String entry = build.getUpdates().get(temp).get("gem");
				if (entry.equalsIgnoreCase(fullName))
					previousGem.set(temp);
			});
			fullName = previousGem.get();
			gem = GemUtils.getGem(fullName);
			initialize();
		});
		return previous;
	}

	public Button getNextButton() {
		Button next = new Button(">>");
		boolean valid = build.getUpdates().containsKey(fullName);
		next.setVisible(valid);
		next.setOnAction(event -> {
			LOGGER.info("Next Selected, Updating {}", gem.isPresent() ? gem.get().getName() : fullName);
			gem = GemUtils.getGem(build.getUpdates().get(fullName).get("gem"));
			fullName = build.getUpdates().get(fullName).get("gem");
			initialize();
		});
		return next;
	}
}