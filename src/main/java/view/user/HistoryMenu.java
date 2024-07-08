package view.user;

import controller.UserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;

import java.io.IOException;
import java.net.URL;

public class HistoryMenu extends Application implements Menuable {

	@FXML
	private TextField numberOfGames;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/HistoryMenu.fxml");
		if (url == null){System.out.println("Couldn't find file: FXML/HistoryMenu.fxml");return;
		}
		Pane root = null;
		try {root = FXMLLoader.load(url);
		} catch (IOException e){throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void run(String input) {
		Result result = null;
		if (UserMenusCommands.GAME_HISTORY.getMatcher(input) != null) {

		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) {
			result = new Result("History Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(input) != null) {
			result = exit();
		} else {
			result = new Result("Invalid command", false);
		}
		System.out.println(result.getMessage());
	}

	private Result gameHistory(int number) {
		return null;
//		return UserMenusController.gameHistory(number);
	}

	public void gameHistory(MouseEvent mouseEvent) {
		try {
			gameHistory(Integer.parseInt(numberOfGames.getText()));
		} catch (NumberFormatException e) {
			System.out.println("Invalid number of games");
		}
	}

	private Result exit() {
		return UserMenusController.exit();
	}

	public void exit(MouseEvent mouseEvent) {
		exit();
	}
}
