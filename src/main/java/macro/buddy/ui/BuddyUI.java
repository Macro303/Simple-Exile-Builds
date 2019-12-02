package macro.buddy.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Buddy.fxml"));
		Parent root = loader.load();
		BuddyController controller = loader.getController();
		controller.setStage(stage);
		stage.setTitle("Exile Buddy");
		Scene scene = new Scene(root, 600, 600);
		scene.getStylesheets().add(getClass().getResource("Dark-Theme.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}