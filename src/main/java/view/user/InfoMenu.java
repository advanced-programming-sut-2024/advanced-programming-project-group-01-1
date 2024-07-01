package view.user;

import controller.UserMenusController;
import model.Result;
import view.Menuable;
import view.game.GameMenusCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class InfoMenu implements Menuable {

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
