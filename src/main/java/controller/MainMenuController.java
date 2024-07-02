package controller;

import model.Result;
import model.user.User;
import view.Appview;
import view.game.prematch.MatchFinderMenu;
import view.sign.login.LoginMenu;
import view.user.ProfileMenu;

public class MainMenuController {

	public static Result logout() {
		Appview.setMenu(new LoginMenu());
		return new Result("Logged out successfully", true);
	}

	public static Result goToProfileMenu() {
		Appview.setMenu(new ProfileMenu());
		return new Result("Entered Profile Menu", true);
	}

	public static Result goToMatchFinderMenu() {
		Appview.setMenu(new MatchFinderMenu());
		return new Result("Entered Match Finder Menu", true);
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static String getLoggedInUsername() {
		return User.getLoggedInUser().getUsername();
	}
}
