package macro.buddy.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class SetupUI extends Application {
	private static final Logger LOGGER = LogManager.getLogger(SetupUI.class);

	public void init(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Setup.fxml"));
		Parent root = loader.load();
		SetupController controller = loader.getController();
		controller.setStage(stage);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Exile Buddy");
		stage.setScene(new Scene(root, 600, 250));
		stage.show();
	}
}