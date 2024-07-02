package controller;

import controller.enums.RegisterMenusResponses;
import controller.enums.Validation;
import model.GameInfo;
import model.Result;
import model.user.User;
import view.Appview;
import view.MainMenu;
import view.user.InfoMenu;
import view.user.ProfileMenu;

import java.util.ArrayList;

public class UserMenusController {

	public static Result changeUsername(String newUsername) {
		if (User.getUserByUsername(newUsername) != null) return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
		else if (!Validation.USERNAME.matches(newUsername)) return RegisterMenusResponses.INVALID_USERNAME.getResult();
		User.getLoggedInUser().setUsername(newUsername);
		return new Result("Username changed successfully", true);
	}

	public static Result changeNickname(String newNickname) {
		User.getLoggedInUser().setNickname(newNickname);
		return new Result("Nickname changed successfully", true);
	}

	public static Result changePassword(String newPassword, String oldPassword) {
		if (!User.getLoggedInUser().getPassword().equals(oldPassword)) return new Result("Incorrect password", false);
		else if (!Validation.CORRECT_PASSWORD.matches(newPassword))
			return RegisterMenusResponses.INVALID_PASSWORD.getResult();
		else if (!Validation.STRONG_PASSWORD.matches(newPassword))
			return RegisterMenusResponses.WEAK_PASSWORD.getResult();
		User.getLoggedInUser().setPassword(newPassword);
		return new Result("Password changed successfully", true);
	}

	public static Result changeEmail(String email) {
		if (!Validation.EMAIL.matches(email)) return RegisterMenusResponses.INVALID_EMAIL.getResult();
		User.getLoggedInUser().setEmail(email);
		return new Result("Email changed successfully", true);
	}


	public static Result goToInfoMenu() {
		User user = User.getLoggedInUser();
		StringBuilder userInfo = new StringBuilder();
		userInfo.append("Username: ").append(user.getUsername()).append("\n");
		userInfo.append("Nickname: ").append(user.getNickname()).append("\n");
		userInfo.append("Max Score: ").append(user.getMaxScore()).append("\n");
		userInfo.append("Rank: ").append(user.getRank()).append("\n");
		userInfo.append("Number of played matches: ").append(user.getNumberOfPlayedMatches()).append("\n");
		userInfo.append("Number of wins: ").append(user.getNumberOfWins()).append("\n");
		userInfo.append("Number of draws: ").append(user.getNumberOfDraws()).append("\n");
		userInfo.append("Number of losses: ").append(user.getNumberOfLosses()).append("\n");
		Appview.setMenu(new InfoMenu());
		Appview.runMenu();
		return new Result(userInfo.toString(), true);
	}

	public static Result showGameHistory(int number) {
		ArrayList<GameInfo> history = User.getLoggedInUser().getHistory();
		StringBuilder gameHistory = new StringBuilder();
		for (int i = 0; i < number; i++) {
			gameHistory.append(history.get(i).toString()).append("\n");
		}
		return new Result(gameHistory.toString(), true);
	}

	public static Result exit() {
		if (Appview.getMenu() instanceof InfoMenu) Appview.setMenu(new ProfileMenu());
		else if (Appview.getMenu() instanceof ProfileMenu) Appview.setMenu(new MainMenu());
		Appview.runMenu();
		return new Result("Exited successfully", true);
	}
}
