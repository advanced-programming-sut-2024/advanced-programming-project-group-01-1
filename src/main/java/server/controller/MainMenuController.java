package server.controller;

import message.Result;
import server.model.Client;
import server.model.user.User;
import server.view.game.prematch.MatchFinderMenu;
import server.view.game.prematch.TVMenu;
import server.view.sign.login.LoginMenu;
import server.view.user.ProfileMenu;
import server.view.user.RankingMenu;
import server.view.user.SocialMenu;

public class MainMenuController {

	public static Result logout(Client client) {
		User.getOnlineUsers().remove(client.getIdentity());
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

	public static Result goToSocialMenu(Client client) {
		System.out.println("go fuck yourself network");
		client.setMenu(new SocialMenu());
		return new Result("Entered Social Menu", true);
	}

	public static Result goToRankingMenu(Client client) {
		client.setMenu(new RankingMenu());
		return new Result("Entered Leaderboard Menu", true);
	}

	public static Result goToTVMenu(Client client) {
		client.setMenu(new TVMenu());
		return new Result("Entered TV Menu", true);
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static Result getLoggedInUsername(Client client) {
		synchronized (User.getUsers()) {
			return new Result(client.getIdentity().getUsername(), true);
		}
	}
}
