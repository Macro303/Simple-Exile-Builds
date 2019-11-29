package macro.buddy.ui;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class BuddyController implements Initializable {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(BuddyController.class);

	@Nullable
	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setStage(@NotNull Stage stage) {
		this.stage = stage;
	}
}
