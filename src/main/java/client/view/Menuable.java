package client.view;

import javafx.stage.Stage;
import message.Result;

public interface Menuable {

	void createStage();

	void start(Stage stage);

	Result run(String input);
}
