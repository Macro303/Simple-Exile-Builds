package macro.buddy.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import macro.buddy.data.CharacterInfo;
import macro.buddy.data.ExileHelper;
import macro.buddy.data.GemInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class BuddyUI extends Application {
	private static final Logger LOGGER = LogManager.getLogger(BuddyUI.class);

	public void init(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		List<GemInfo> gems = ExileHelper.getGems();
		LOGGER.info("Gems: " + gems);
		List<CharacterInfo> characters = ExileHelper.getCharacters();
		LOGGER.info("Characters: " + characters);
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("Buddy.fxml"));
		Parent root = loader.load();
		BuddyController controller = loader.getController();
		controller.setStage(stage);
		stage.initStyle(StageStyle.DECORATED);
		stage.setTitle("Exile Buddy");
		stage.setScene(new Scene(root, 600, 600));
		stage.show();*/
	}
}