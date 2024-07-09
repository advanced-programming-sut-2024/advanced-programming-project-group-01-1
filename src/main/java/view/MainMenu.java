package view;

import controller.MainMenuController;
import controller.UserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;

import java.net.URL;

import static controller.MainMenuController.*;

public class MainMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage() {
		launch();
	}

	public Label username;

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/MainMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/MainMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		username.setText(MainMenuController.getLoggedInUsername());
	}

	public void newGame(MouseEvent mouseEvent) {
		Result result = goToMatchFinderMenu();
	}

	public void profile(MouseEvent mouseEvent) {
		Result result = goToProfileMenu();
	}

	public void leaderboard(MouseEvent mouseEvent) {
		Result result = UserMenusController.goToRankingMenu();
	}

	public void tv(MouseEvent mouseEvent) {
		Result result = new Result("coming soon!!!", true);
		AlertMaker.makeAlert("GoGo TV", result);
	}

	public void logout(MouseEvent mouseEvent) {
		Result result = MainMenuController.logout();
		AlertMaker.makeAlert("Logout", result);
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(input) != null) result = goToMatchFinderMenu();
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(input) != null) result = goToProfileMenu();
		else if (MainMenuCommands.LOGOUT.getMatcher(input) != null) result = MainMenuController.logout();
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else result = new Result("Invalid command", false);
		System.out.println(result);
	}

}
