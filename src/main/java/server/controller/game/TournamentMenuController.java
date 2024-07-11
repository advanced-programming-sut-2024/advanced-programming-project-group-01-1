package server.controller.game;

import client.main.TCPClient;
import message.Result;
import message.UserMenusCommands;
import server.model.Client;
import server.model.tournament.Bracket;
import server.model.user.User;
import server.view.MainMenu;
import server.view.game.prematch.LobbyMenu;
import server.view.game.prematch.MatchFinderMenu;

import java.util.Objects;

public class TournamentMenuController {

	public static Result getTournamentInfo(Client client) {
		Bracket bracket = client.getIdentity().getCurrentBracket();
		if (bracket == null)
			return new Result("No tournament is running", false);
		return new Result(bracket.toString(), true);
	}

	public static Result startBracketMatch(User player1, User player2) {
		System.out.println("Starting match between " + player1.getUsername() + " and " + player2.getUsername());
		Client client1 = Client.getClient(player1), client2 = Client.getClient(player2);
		client1.getIdentity().setChallengedUser(player2);
		client2.getIdentity().setChallengedUser(player1);
		client1.setInGame(true);
		client2.setInGame(true);
		client1.setMenu(new LobbyMenu());
		client2.setMenu(new LobbyMenu());
		return new Result("Match started", true);
	}

	public static Result setPlayerReady(Client client) {
		Bracket bracket = client.getIdentity().getCurrentBracket();
		if (bracket == null)
			return new Result("No tournament is running", false);
		bracket.setPlayerReady(client.getIdentity());
		return new Result("You are ready", true);
	}

	public static Result checkRequest(Client client) {
		boolean started = client.getIdentity().getChallengedUser() != null;
		if (!started)
			return new Result("Game hasn't started yet", false);
		return new Result("Go to Lobby", true);
	}

	public static Result exit(Client client) {
		client.setMenu(new MatchFinderMenu());
		return new Result("Entering match finder menu", true);
	}

	public static Result endTournament(Client client) {
		client.getIdentity().setCurrentBracket(null);
		client.setMenu(new MainMenu());
		return new Result("Tournament ended", true);
	}

	public static Result getUsername(Client client) {
		return new Result(client.getIdentity().getUsername(), true);
	}

}
