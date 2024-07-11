package server.view.game;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.TVMenuController;
import server.controller.game.TournamentMenuController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class TournamentMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result = null;
		if (GameMenusCommands.GET_TOURNAMENT_INFO.getMatcher(command) != null)
			result = TournamentMenuController.getTournamentInfo(client);
		else if (GameMenusCommands.READY.getMatcher(command) != null)
			result = TournamentMenuController.setPlayerReady(client);
		else if (GameMenusCommands.CHECK_REQUEST.getMatcher(command) != null)
			result = TournamentMenuController.checkRequest(client);
		else if (GameMenusCommands.GET_USERNAMES.getMatcher(command) != null)
			result = TournamentMenuController.getUsername(client);
		else if ((matcher = GameMenusCommands.SPECTATE.getMatcher(command)) != null)
			result = spectate(client, matcher);
		else if ( GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(command) != null)
			result = TournamentMenuController.exit(client);
		else if (GameMenusCommands.END_TOURNAMENT.getMatcher(command) != null)
			result = TournamentMenuController.endTournament(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result spectate(Client client, Matcher matcher) {
		String username1 = matcher.group("username1"), username2 = matcher.group("username2");
		return TVMenuController.spectate(client, username1, username2);
	}

}
