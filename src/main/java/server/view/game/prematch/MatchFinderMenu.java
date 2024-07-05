package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class MatchFinderMenu implements Menuable {

	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.CREATE_GAME.getMatcher(input)) != null) result = createGame(client, matcher);
		else if (GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(input) != null)
			result = PreMatchMenusController.getOtherUsernames(client);
		else if (GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(input) != null)
			result = PreMatchMenusController.exit(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result createGame(Client client, Matcher matcher) {
		String opponent = matcher.group("opponent");
		return PreMatchMenusController.createGame(client, opponent);
	}
}
