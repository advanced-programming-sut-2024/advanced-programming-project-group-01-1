package server.controller;

import message.Result;
import server.model.Client;
import server.model.user.User;
import server.view.game.prematch.MatchFinderMenu;
import server.view.sign.login.LoginMenu;
import server.view.user.ProfileMenu;

public class MainMenuController {

	public static Result logout(Client client) {
		Appview.setMenu(new LoginMenu());
		return new Result("Logged out successfully", true);
	}

	public static Result goToProfileMenu(Client client) {
		Appview.setMenu(new ProfileMenu());
		return new Result("Entered Profile Menu", true);
	}

	public static Result goToMatchFinderMenu(Client client) {
		Appview.setMenu(new MatchFinderMenu());
		return new Result("Entered Match Finder Menu", true);
	}

	public static Result showCurrentMenu(Client client) {
		return new Result("Main Menu", true);
	}

	public static String getLoggedInUsername(Client client) {
		return User.getLoggedInUser().getUsername();
	}
}
