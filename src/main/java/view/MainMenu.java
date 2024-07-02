package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Result;

import static controller.MainMenuController.*;

public class MainMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		// TODO:
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(input) != null) result = goToMatchFinderMenu();
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(input) != null) result = goToProfileMenu();
		else if (MainMenuCommands.LOGOUT.getMatcher(input) != null) result = logout();
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else result = new Result("Invalid command", false);
		System.out.println(result);
	}

}
