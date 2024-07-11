package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.TVMenuController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class TVMenu implements Menuable {

	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if (GameMenusCommands.GET_GAMES.getMatcher(input) != null) {
			result = TVMenuController.getGames();
		} else if ((matcher = GameMenusCommands.SPECTATE.getMatcher(input)) != null) {
			result = spectate(client, matcher);
		} else if (GameMenusCommands.BACK.getMatcher(input) != null) {
			result = TVMenuController.back(client);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result spectate(Client client, Matcher matcher) {
		String username1 = matcher.group("username1");
		String username2 = matcher.group("username2");
		return TVMenuController.spectate(client, username1, username2);
	}
}
