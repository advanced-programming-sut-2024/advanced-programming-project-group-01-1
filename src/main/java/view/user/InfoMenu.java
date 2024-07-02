package view.user;

import controller.UserMenusController;
import javafx.stage.Stage;
import model.Result;
import view.Menuable;
import view.game.GameMenusCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class InfoMenu implements Menuable {

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
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(input)) != null) {
			result = showGameHistory(matcher);
		} else if ((matcher = UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = UserMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit();
		} else {
			result = new Result("Invalid command", false);
		}
	}

	private Result showGameHistory(Matcher matcher) {
		int numberOfGames = (matcher.group("numberOfGames") != null ? Integer.parseInt(matcher.group("numberOfGames")) : 5);
		return UserMenusController.showGameHistory(numberOfGames);
	}

	private Result showCurrentMenu() {
		return new Result("User Info Menu", true);
	}

	private Result exit() {
		return UserMenusController.exit();
	}

}
