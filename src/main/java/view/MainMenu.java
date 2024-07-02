package view;

import javafx.stage.Stage;
import model.Result;
import view.game.prematch.LobbyMenu;

import java.util.regex.Matcher;

import static controller.MainMenuController.*;
import static javafx.application.Application.launch;

public class MainMenu implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
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
		Matcher matcher;
		Result result;
		if ((matcher = MainMenuCommands.ENTER_GAME_MENU.getMatcher(input)) != null) {
			result = goToMatchFinderMenu();
		} else if ((matcher = MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(input)) != null) {
			result = goToProfileMenu();
		} else if ((matcher = MainMenuCommands.LOGOUT.getMatcher(input)) != null) {
			result = logout();
		} else {
			result = new Result("Invalid command", false);
		}
	}

}
