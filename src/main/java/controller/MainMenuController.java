package controller;

import model.Result;
import view.Appview;
import view.game.prematch.MatchFinderMenu;
import view.sign.login.LoginMenu;
import view.user.ProfileMenu;

public class MainMenuController {

	public static Result logout() {
		Appview.setMenu(new LoginMenu());
		Appview.runMenu();
		return new Result("Logged out successfully", true);
	}

	public static Result goToProfileMenu() {
		Appview.setMenu(new ProfileMenu());
		Appview.runMenu();
		return new Result("Entered Profile Menu", true);
	}

	public static Result goToMatchFinderMenu() {
		Appview.setMenu(new MatchFinderMenu());
		Appview.runMenu();
		return new Result("Entered Match Finder Menu", true);
	}

}
