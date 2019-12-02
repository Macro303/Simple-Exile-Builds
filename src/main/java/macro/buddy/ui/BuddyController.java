package macro.buddy.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import macro.buddy.Util;
import macro.buddy.data.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class BuddyController implements Initializable {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(BuddyController.class);
	@FXML
	private VBox tabI;
	@FXML
	private VBox tabII;
	@FXML
	private VBox tabIII;
	@FXML
	private VBox tabIV;
	@FXML
	private VBox tabV;
	@FXML
	private VBox tabVI;
	@FXML
	private VBox tabVII;
	@FXML
	private VBox tabVIII;
	@FXML
	private VBox tabIX;
	@FXML
	private VBox tabX;
	@FXML
	private Tab itemTab;
	@FXML
	private Tab questTab;
	@FXML
	private Tab skillTab;

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemTab.setDisable(true);
		questTab.setDisable(true);
		skillTab.setDisable(true);

		setupGemTab();
	}

	public void setStage(@NotNull Stage stage) {
		this.stage = stage;
	}

	private void setupGemTab() {
		for (ActInfo act : ExileHelper.getGems()) {
			VBox actTab = act.getAct() == 1 ? tabI : act.getAct() == 2 ? tabII : act.getAct() == 3 ? tabIII : act.getAct() == 4 ? tabIV : act.getAct() == 5 ? tabV : act.getAct() == 6 ? tabVI : act.getAct() == 7 ? tabVII : tabVIII;
			//Quests
			for (QuestInfo quest : act.getQuests()) {
				Label questLabel = new Label(quest.getName());
				actTab.getChildren().add(questLabel);
				List<GemInfo> rewards = quest.getRewards().getOrDefault(Util.selectedBuild.getClassTag(), new ArrayList<>());
				for (GemInfo gem : rewards) {
					HBox gemBox = new HBox();
					gemBox.setSpacing(5.0);
					CheckBox gemCheck = new CheckBox();
					if (Util.selectedBuild.getGemList().contains(gem.getName()))
						gemCheck.setSelected(true);
					gemCheck.setDisable(true);
					Label gemLabel = new Label(gem.getName() + " [" + gem.getTags().stream().map(Enum::name).collect(Collectors.joining(", ")) + "]");
					if (Util.strToColour(gem.getColour()) != null)
						gemLabel.setStyle("-fx-text-fill: " + Util.strToColour(gem.getColour()));
					gemBox.getChildren().addAll(gemCheck, gemLabel);
					actTab.getChildren().add(gemBox);
				}
			}
			//Vendors
			for (VendorInfo vendor : act.getVendors()) {
				Label vendorLabel = new Label(vendor.getName() + " - " + vendor.getNpc());
				actTab.getChildren().add(vendorLabel);
				List<GemInfo> rewards = vendor.getRewards().getOrDefault(Util.selectedBuild.getClassTag(), new ArrayList<>());
				for (GemInfo gem : rewards) {
					HBox gemBox = new HBox();
					gemBox.setSpacing(5.0);
					CheckBox gemCheck = new CheckBox();
					if (Util.selectedBuild.getGemList().contains(gem.getName()))
						gemCheck.setSelected(true);
					gemCheck.setDisable(true);
					Label gemLabel = new Label(gem.getName() + " [" + gem.getTags().stream().map(Enum::name).collect(Collectors.joining(", ")) + "]");
					if (Util.strToColour(gem.getColour()) != null)
						gemLabel.setStyle("-fx-text-fill: " + Util.strToColour(gem.getColour()));
					gemBox.getChildren().addAll(gemCheck, gemLabel);
					actTab.getChildren().add(gemBox);
				}
			}
		}
	}
}
