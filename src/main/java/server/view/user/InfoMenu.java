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
		if (UserMenusCommands.GET_USERNAME.getMatcher(command) != null)
			result = UserMenusController.getUsername(client);
		else if (UserMenusCommands.GET_NICKNAME.getMatcher(command) != null)
			result = UserMenusController.getNickname(client);
		else if (UserMenusCommands.GET_MAX_SCORE.getMatcher(command) != null)
			result = UserMenusController.getMaxScore(client);
		else if (UserMenusCommands.GET_RANK.getMatcher(command) != null) result = UserMenusController.getRank(client);
		else if (UserMenusCommands.GET_PLAYED_MATCHES.getMatcher(command) != null)
			result = UserMenusController.getNumberOfPlayedMatches(client);
		else if (UserMenusCommands.GET_WINS.getMatcher(command) != null)
			result = UserMenusController.getNumberOfWins(client);
		else if (UserMenusCommands.GET_DRAWS.getMatcher(command) != null)
			result = UserMenusController.getNumberOfDraws(client);
		else if (UserMenusCommands.GET_LOSSES.getMatcher(command) != null)
			result = UserMenusController.getNumberOfLosses(client);
		else if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(command)) != null)
			result = showGameHistory(client, matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = showCurrentMenu();
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
