package server.view.user;

import javafx.fxml.FXML;
import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class InfoMenu implements Menuable {
	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(command)) != null)
			result = showGameHistory(client, matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command) != null) result = exit(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showGameHistory(Client client, Matcher matcher) {
		int numberOfGames = (matcher.group("numberOfGames") != null ? Integer.parseInt(matcher.group("numberOfGames")) : 5);
		return UserMenusController.showGameHistory(client, numberOfGames);
	}

	private Result showCurrentMenu() {
		return new Result("User Info Menu", true);
	}

	@FXML
	private Result exit(Client client) {
		return UserMenusController.exit(client);
	}

}
