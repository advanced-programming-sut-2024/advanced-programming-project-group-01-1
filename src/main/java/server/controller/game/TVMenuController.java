package server.controller.game;

import message.Result;
import server.model.Client;
import server.model.game.Game;
import server.model.user.User;
import server.view.MainMenu;
import server.view.game.MatchMenu;

public class TVMenuController {

	public static Result getGames() {
		StringBuilder games = new StringBuilder();
		for (Game game : Game.getGames()) {
			games.append(game.toString());
			games.append("\n---------------------\n");
		}
		return new Result(games.toString(), true);
	}

	public static Result spectate(Client client, String username1, String username2) {
		Game game = Game.getGame(User.getUserByUsername(username1), User.getUserByUsername(username2));
		if (game == null) return new Result("no such game is running", false);
		client.setStreamingGame(game);
		client.setMenu(new MatchMenu());
		return new Result("go to stream", true);
	}

	public static Result back(Client client) {
		client.setMenu(new MainMenu());
		return new Result("back to main menu", true);
	}
}
