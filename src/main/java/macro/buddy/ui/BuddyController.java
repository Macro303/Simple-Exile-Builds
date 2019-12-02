package macro.buddy.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import macro.buddy.Util;
import macro.buddy.config.Config;
import macro.buddy.data.ExileHelper;
import macro.buddy.data.GemInfo;
import macro.buddy.data.QuestInfo;
import macro.buddy.data.VendorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.*;
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
		List<GemInfo> gems = ExileHelper.getGems();
		for (int act = 1; act <= 10; act++) {
			int counter = act;
			VBox actTab = act == 1 ? tabI : act == 2 ? tabII : act == 3 ? tabIII : act == 4 ? tabIV : act == 5 ? tabV : act == 6 ? tabVI : act == 7 ? tabVII : tabVIII;
			//Quests
			List<GemInfo> filteredGems = gems.stream().filter(gem -> gem.getQuests().stream().anyMatch(info -> {
				boolean validAct = info.getAct() == counter;
				boolean validClass = info.getClassList().contains(Util.selectedBuild.getClassTag());
				return validAct && validClass;
			})).collect(Collectors.toList());
			SortedSet<String> quests = new TreeSet<>();
			for (GemInfo gem : filteredGems)
				quests.addAll(gem.getQuests().stream().map(QuestInfo::getName).collect(Collectors.toSet()));
			for (String quest : quests) {
				List<GemInfo> questGems = filteredGems.stream().filter(gem -> gem.getQuests().stream().anyMatch(info -> {
					boolean validAct = info.getAct() == counter;
					boolean validQuest = info.getName().equals(quest);
					boolean validClass = info.getClassList().contains(Util.selectedBuild.getClassTag());
					return validAct && validQuest && validClass;
				})).collect(Collectors.toList());
				if (questGems.size() > 0) {
					Label questLabel = new Label(quest);
					actTab.getChildren().add(questLabel);
					questGems.sort(Comparator.comparing(GemInfo::getName));
					for (GemInfo gem : questGems) {
						HBox gemBox = new HBox();
						gemBox.setSpacing(5.0);
						CheckBox gemCheck = new CheckBox();
						if (Util.selectedBuild.getGemList().contains(gem.getName()))
							gemCheck.setSelected(true);
						gemCheck.setDisable(true);
						Label gemLabel = new Label(gem.getName() + " [" + gem.getTags().stream().map(Enum::name).collect(Collectors.joining(", ")) + "]");
						if (!gem.getColour().equals("White"))
							gemLabel.setStyle("-fx-text-fill: " + gem.getColour());
						gemBox.getChildren().addAll(gemCheck, gemLabel);
						actTab.getChildren().add(gemBox);
					}
				}
			}
			//Vendors
			filteredGems = gems.stream().filter(gem -> gem.getVendors().stream().anyMatch(info -> {
				boolean validAct = info.getAct() == counter;
				boolean validClass = info.getClassList().contains(Util.selectedBuild.getClassTag());
				return validAct && validClass;
			})).collect(Collectors.toList());
			SortedSet<String> vendors = new TreeSet<>();
			for (GemInfo gem : filteredGems)
				vendors.addAll(gem.getVendors().stream().map(vendor -> vendor.getName() + " - " + vendor.getNpc()).collect(Collectors.toSet()));
			for (String vendor : vendors) {
				List<GemInfo> vendorGems = filteredGems.stream().filter(gem -> gem.getVendors().stream().anyMatch(info -> {
					boolean validAct = info.getAct() == counter;
					boolean validQuest = (info.getName() + " - " + info.getNpc()).equals(vendor);
					boolean validClass = info.getClassList().contains(Util.selectedBuild.getClassTag());
					return validAct && validQuest && validClass;
				})).collect(Collectors.toList());
				if (vendorGems.size() > 0) {
					Label vendorLabel = new Label(vendor);
					actTab.getChildren().add(vendorLabel);
					vendorGems.sort(Comparator.comparing(GemInfo::getName));
					for (GemInfo gem : vendorGems) {
						HBox gemBox = new HBox();
						gemBox.setSpacing(5.0);
						CheckBox gemCheck = new CheckBox();
						if (Util.selectedBuild.getGemList().contains(gem.getName()))
							gemCheck.setSelected(true);
						gemCheck.setDisable(true);
						Label gemLabel = new Label(gem.getName() + " [" + gem.getTags().stream().map(Enum::name).collect(Collectors.joining(", ")) + "]");
						if (!gem.getColour().equals("White"))
							gemLabel.setStyle("-fx-text-fill: " + gem.getColour());
						gemBox.getChildren().addAll(gemCheck, gemLabel);
						actTab.getChildren().add(gemBox);
					}
				}
			}
		}
	}
}
