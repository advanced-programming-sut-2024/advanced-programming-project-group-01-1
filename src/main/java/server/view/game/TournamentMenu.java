package server.view.game;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.TournamentMenuController;
import server.model.Client;
import server.view.Menuable;

public class TournamentMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Result result = null;
		if (GameMenusCommands.GET_TOURNAMENT_INFO.getMatcher(command) != null) {
			result = TournamentMenuController.getTournamentInfo(client);
		} else if (GameMenusCommands.READY.getMatcher(command) != null) {
			result = TournamentMenuController.setPlayerReady(client);
		} else if (GameMenusCommands.CHECK_REQUEST.getMatcher(command) != null) {
			System.out.println(client.getIdentity().getUsername() + " - " + client.getIdentity().getChallengedUser());
			result = TournamentMenuController.checkRequest(client);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}
}
