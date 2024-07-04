package server.view.user;

import javafx.fxml.FXML;
import message.Command;
import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class InfoMenu implements Menuable {
	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(command.getCommand())) != null)
			result = showGameHistory(matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command.getCommand()) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showGameHistory(Matcher matcher) {
		int numberOfGames = (matcher.group("numberOfGames") != null ? Integer.parseInt(matcher.group("numberOfGames")) : 5);
		return UserMenusController.showGameHistory(numberOfGames);
	}

	private Result showCurrentMenu() {
		return new Result("User Info Menu", true);
	}

	@FXML
	private Result exit() {
		return UserMenusController.exit();
	}

}
