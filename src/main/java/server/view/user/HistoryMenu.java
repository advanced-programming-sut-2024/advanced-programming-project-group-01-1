package server.view.user;

import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class HistoryMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Result result = null;
		Matcher matcher;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(command)) != null) {
			String numberOfGames = matcher.group("numberOfGames");
			result = UserMenusController.showGameHistory(client, numberOfGames != null ? Integer.parseInt(matcher.group("numberOfGames")) : 0);
		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) {
			result = new Result("History Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(command) != null) {
			result = UserMenusController.exit(client);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

}
