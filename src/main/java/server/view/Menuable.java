package server.view;

import javafx.stage.Stage;
import message.Command;
import message.Result;

public interface Menuable {

	void createStage();

	void start(Stage stage);

	Result run(Command command);
}
