package server.controller.game;

import message.Result;
import server.model.Client;
import server.model.tournament.Bracket;

public class TournamentMenuController {

	public static Result getTournamentInfo(Client client) {
		Bracket bracket = client.getIdentity().getCurrentBracket();
		if (bracket == null)
			return new Result("No tournament is running", false);
		return new Result(bracket.toString(), true);
	}

}
