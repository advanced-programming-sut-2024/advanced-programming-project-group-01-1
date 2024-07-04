package server.controller;

import server.controller.enums.RegisterMenusResponses;
import server.controller.enums.Validation;
import server.model.Client;
import server.model.GameInfo;
import message.Result;
import server.model.user.User;
import server.view.MainMenu;
import server.view.user.InfoMenu;
import server.view.user.ProfileMenu;

import java.util.ArrayList;

public class UserMenusController {

	public static Result changeUsername(Client client, String newUsername) {
		if (User.getUserByUsername(newUsername) != null) return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
		else if (!Validation.USERNAME.matches(newUsername)) return RegisterMenusResponses.INVALID_USERNAME.getResult();
		User.getLoggedInUser().setUsername(newUsername);
		return new Result("Username changed successfully", true);
	}

	public static Result changeNickname(Client client,String newNickname) {
		User.getLoggedInUser().setNickname(newNickname);
		return new Result("Nickname changed successfully", true);
	}

	public static Result changePassword(Client client,String newPassword, String oldPassword) {
		if (!User.getLoggedInUser().getPassword().equals(oldPassword)) return new Result("Incorrect password", false);
		else if (!Validation.CORRECT_PASSWORD.matches(newPassword))
			return RegisterMenusResponses.INVALID_PASSWORD.getResult();
		else if (!Validation.STRONG_PASSWORD.matches(newPassword))
			return RegisterMenusResponses.WEAK_PASSWORD.getResult();
		User.getLoggedInUser().setPassword(newPassword);
		return new Result("Password changed successfully", true);
	}

	public static Result changeEmail(Client client,String email) {
		if (!Validation.EMAIL.matches(email)) return RegisterMenusResponses.INVALID_EMAIL.getResult();
		User.getLoggedInUser().setEmail(email);
		return new Result("Email changed successfully", true);
	}


	public static Result goToInfoMenu(Client client) {
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
		return new Result(userInfo.toString(), true);
	}

	public static Result showGameHistory(Client client,int number) {
		ArrayList<GameInfo> history = User.getLoggedInUser().getHistory();
		StringBuilder gameHistory = new StringBuilder();
		for (int i = 0; i < number; i++) {
			gameHistory.append(history.get(i).toString()).append("\n");
		}
		return new Result(gameHistory.toString(), true);
	}

	public static Result exit(Client client) {
		if (Appview.getMenu() instanceof InfoMenu) Appview.setMenu(new ProfileMenu());
		else if (Appview.getMenu() instanceof ProfileMenu) Appview.setMenu(new MainMenu());
		return new Result("Exited successfully", true);
	}

	public static Result saveChanges(Client client,String username, String nickname, String email, String oldPassword, String newPassword, String confirmNewPassword) {
		if (!oldPassword.equals(User.getLoggedInUser().getPassword()))
			return new Result("Wrong Password", false);
		if (!username.equals(User.getLoggedInUser().getUsername()) && User.getUserByUsername(username) != null)
			return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
		else if (!Validation.USERNAME.matches(username))
			return RegisterMenusResponses.INVALID_USERNAME.getResult();
		else if (!Validation.EMAIL.matches(email))
			return RegisterMenusResponses.INVALID_EMAIL.getResult();
		else if (!newPassword.equals(confirmNewPassword))
			return RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult();
		else if (!newPassword.equals("")) {
			if (!Validation.CORRECT_PASSWORD.matches(newPassword))
				return RegisterMenusResponses.INVALID_PASSWORD.getResult();
			else if (!Validation.STRONG_PASSWORD.matches(newPassword))
				return RegisterMenusResponses.WEAK_PASSWORD.getResult();
		}
		User.getLoggedInUser().setUsername(username);
		User.getLoggedInUser().setNickname(nickname);
		User.getLoggedInUser().setEmail(email);
		if (!newPassword.equals("")) User.getLoggedInUser().setPassword(newPassword);
		return new Result("Your data updated successfully", true);
	}
}
