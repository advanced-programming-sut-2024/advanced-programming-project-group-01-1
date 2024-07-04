package server.controller;

import message.Result;
import server.model.Client;
import server.view.game.prematch.MatchFinderMenu;
import server.view.sign.login.LoginMenu;
import server.view.user.ProfileMenu;

public class MainMenuController {

	public static Result logout(Client client) {
		client.setIdentity(null);
		client.setMenu(new LoginMenu());
		return new Result("Logged out successfully", true);
	}

	public static Result goToProfileMenu(Client client) {
		client.setMenu(new ProfileMenu());
		return new Result("Entered Profile Menu", true);
	}

	public static Result goToMatchFinderMenu(Client client) {
		client.setMenu(new MatchFinderMenu());
		return new Result("Entered Match Finder Menu", true);
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static Result getLoggedInUsername(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(client.getIdentity().getUsername(), true);
		}
	}
}
