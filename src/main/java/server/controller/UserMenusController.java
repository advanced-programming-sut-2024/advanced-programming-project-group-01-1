package server.controller;

import message.Result;
import server.controller.enums.RegisterMenusResponses;
import server.controller.enums.Validation;
import server.model.Client;
import server.model.GameInfo;
import server.model.user.User;
import server.view.MainMenu;
import server.view.user.InfoMenu;
import server.view.user.ProfileMenu;

import java.util.ArrayList;

public class UserMenusController {

	public static Result changeUsername(Client client, String newUsername) {
		synchronized (User.getUsers()) {
			synchronized (client.getIdentity()) {
				if (User.getUserByUsername(newUsername) != null)
					return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
				else if (!Validation.USERNAME.matches(newUsername))
					return RegisterMenusResponses.INVALID_USERNAME.getResult();
				client.getIdentity().setUsername(newUsername);
				return new Result("Username changed successfully", true);
			}
		}
	}

	public static Result changeNickname(Client client, String newNickname) {
		synchronized (client.getIdentity()) {
			client.getIdentity().setNickname(newNickname);
			return new Result("Nickname changed successfully", true);
		}
	}

	public static Result changePassword(Client client, String newPassword, String oldPassword) {
		synchronized (client.getIdentity()) {
			if (!client.getIdentity().getPassword().equals(oldPassword)) return new Result("Incorrect password", false);
			else if (!Validation.CORRECT_PASSWORD.matches(newPassword))
				return RegisterMenusResponses.INVALID_PASSWORD.getResult();
			else if (!Validation.STRONG_PASSWORD.matches(newPassword))
				return RegisterMenusResponses.WEAK_PASSWORD.getResult();
			client.getIdentity().setPassword(newPassword);
			return new Result("Password changed successfully", true);
		}
	}

	public static Result changeEmail(Client client, String email) {
		synchronized (client.getIdentity()) {
			if (!Validation.EMAIL.matches(email)) return RegisterMenusResponses.INVALID_EMAIL.getResult();
			client.getIdentity().setEmail(email);
			return new Result("Email changed successfully", true);
		}
	}


	public static Result goToInfoMenu(Client client) {
		synchronized (client.getIdentity()) {
			User user = client.getIdentity();
			StringBuilder userInfo = new StringBuilder();
			userInfo.append("Username: ").append(user.getUsername()).append("\n");
			userInfo.append("Nickname: ").append(user.getNickname()).append("\n");
			userInfo.append("Max Score: ").append(user.getMaxScore()).append("\n");
			userInfo.append("Rank: ").append(user.getRank()).append("\n");
			userInfo.append("Number of played matches: ").append(user.getNumberOfPlayedMatches()).append("\n");
			userInfo.append("Number of wins: ").append(user.getNumberOfWins()).append("\n");
			userInfo.append("Number of draws: ").append(user.getNumberOfDraws()).append("\n");
			userInfo.append("Number of losses: ").append(user.getNumberOfLosses()).append("\n");
			client.setMenu(new InfoMenu());
			return new Result(userInfo.toString(), true);
		}
	}

	public static Result showGameHistory(Client client, int number) {
		synchronized (client.getIdentity()) {
			ArrayList<GameInfo> history = client.getIdentity().getHistory();
			StringBuilder gameHistory = new StringBuilder();
			for (int i = 0; i < number; i++) {
				gameHistory.append(history.get(i).toString()).append("\n");
			}
			return new Result(gameHistory.toString(), true);
		}
	}

	public static Result exit(Client client) {
		if (client.getMenu() instanceof InfoMenu) client.setMenu(new ProfileMenu());
		else if (client.getMenu() instanceof ProfileMenu) client.setMenu(new MainMenu());
		return new Result("Exited successfully", true);
	}

	public static Result saveChanges(Client client, String username, String nickname, String email, String oldPassword, String newPassword, String confirmNewPassword) {
		synchronized (User.getUsers()) {
			synchronized (client.getIdentity()) {
				if (!oldPassword.equals(client.getIdentity().getPassword())) return new Result("Wrong Password", false);
				if (!username.equals(client.getIdentity().getUsername()) && User.getUserByUsername(username) != null)
					return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
				else if (!Validation.USERNAME.matches(username))
					return RegisterMenusResponses.INVALID_USERNAME.getResult();
				else if (!Validation.EMAIL.matches(email)) return RegisterMenusResponses.INVALID_EMAIL.getResult();
				else if (!newPassword.equals(confirmNewPassword))
					return RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult();
				else if (!newPassword.equals("")) {
					if (!Validation.CORRECT_PASSWORD.matches(newPassword))
						return RegisterMenusResponses.INVALID_PASSWORD.getResult();
					else if (!Validation.STRONG_PASSWORD.matches(newPassword))
						return RegisterMenusResponses.WEAK_PASSWORD.getResult();
				}
				client.getIdentity().setUsername(username);
				client.getIdentity().setNickname(nickname);
				client.getIdentity().setEmail(email);
				if (!newPassword.equals("")) client.getIdentity().setPassword(newPassword);
				return new Result("Your data updated successfully", true);
			}
		}
	}

	public static Result getUsername(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(client.getIdentity().getUsername(), true);
		}
	}

	public static Result getNickname(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(client.getIdentity().getNickname(), true);
		}
	}

	public static Result getEmail(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(client.getIdentity().getEmail(), true);
		}
	}

	public static Result getMaxScore(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(String.valueOf(client.getIdentity().getMaxScore()), true);
		}
	}

	public static Result getRank(Client client) {
		synchronized (User.getUsers()) {
			synchronized (client.getIdentity()) {
				return new Result(String.valueOf(client.getIdentity().getRank()), true);
			}
		}
	}

	public static Result getNumberOfPlayedMatches(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(String.valueOf(client.getIdentity().getNumberOfPlayedMatches()), true);
		}
	}

	public static Result getNumberOfWins(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(String.valueOf(client.getIdentity().getNumberOfWins()), true);
		}
	}

	public static Result getNumberOfDraws(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(String.valueOf(client.getIdentity().getNumberOfDraws()), true);
		}
	}

	public static Result getNumberOfLosses(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(String.valueOf(client.getIdentity().getNumberOfLosses()), true);
		}
	}
}
