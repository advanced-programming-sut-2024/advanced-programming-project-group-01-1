package controller;

import controller.enums.RegisterMenusResponses;
import controller.enums.Validation;
import model.GameInfo;
import model.Result;
import model.user.User;
import view.Appview;
import view.MainMenu;
import view.user.HistoryMenu;
import view.user.InfoMenu;
import view.user.ProfileMenu;
import view.user.RankingMenu;

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
		userInfo.append("Max Score: ").append(user.getMaxElo()).append("\n");
		userInfo.append("Rank: ").append(user.getRank()).append("\n");
		userInfo.append("Number of played matches: ").append(user.getNumberOfPlayedMatches()).append("\n");
		userInfo.append("Number of wins: ").append(user.getNumberOfWins()).append("\n");
		userInfo.append("Number of draws: ").append(user.getNumberOfDraws()).append("\n");
		userInfo.append("Number of losses: ").append(user.getNumberOfLosses()).append("\n");
		Appview.setMenu(new InfoMenu());
		return new Result(userInfo.toString(), true);
	}

	public static Result goToHistoryMenu() {
		Appview.setMenu(new HistoryMenu());
		return new Result("Entering History Menu", true);
	}

	public static Result showGameHistory(int number) {
		ArrayList<GameInfo> history = User.getLoggedInUser().getHistory();
		StringBuilder gameHistory = new StringBuilder();
		int start = Math.max(0, history.size() - number), end = history.size();
		for (int i = end - 1; i >= start; i--) {
			gameHistory.append(history.get(i).toString()).append("\n\n");
		}
		return new Result(gameHistory.toString(), true);
	}

	public static Result exit() {
		if (Appview.getMenu() instanceof InfoMenu) Appview.setMenu(new ProfileMenu());
		else if (Appview.getMenu() instanceof ProfileMenu) Appview.setMenu(new MainMenu());
		else if (Appview.getMenu() instanceof RankingMenu) Appview.setMenu(new MainMenu());
		else if (Appview.getMenu() instanceof HistoryMenu) Appview.setMenu(new InfoMenu());
		return new Result("Exited successfully", true);
	}

	public static Result saveChanges(String username, String nickname, String email, String oldPassword, String newPassword, String confirmNewPassword) {
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

	public static Result getPage(int pageNumber) {
		String page = "";
		ArrayList<User> sortedUsers = User.getUsers();
		sortedUsers.sort((o1, o2) -> {
			if (o1.getRank() == o2.getRank()) return o1.getUsername().compareTo(o2.getUsername());
			return o1.getRank() - o2.getRank();
		});
		int start = (pageNumber - 1) * 10 + 1, end = Math.min(pageNumber * 10, sortedUsers.size());
		for (int i = start; i <= end; i++) {
			User user = User.getUsers().get(i - 1);
			page += user.getRank() + " " + user.getUsername() + " " + (int) user.getElo() + "\n";
		}
		return new Result(page, true);
	}

	public static Result getPageCount() {
		int pageCount = (User.getUsers().size() + 9) / 10;
		return new Result(String.valueOf(pageCount), true);
	}

}
