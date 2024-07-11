package client.controller;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.user.*;
import message.Result;
import message.UserMenusCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ClientUserMenusController {

	public static Thread friendListUpdater, leaderboardUpdater;

	public static Result goToHistoryMenu() {
		String command = UserMenusCommands.GO_TO_HISTORY_MENU.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientHistoryMenu());
		return result;
	}

	public static Result changeUsername(String newUsername) {
		String command = UserMenusCommands.CHANGE_USERNAME.getPattern();
		command = command.replace("(?<username>\\S+)", newUsername);
		return TCPClient.send(command);
	}

	public static Result changeNickname(String newNickname) {
		String command = UserMenusCommands.CHANGE_NICKNAME.getPattern();
		command = command.replace("(?<nickname>\\S+)", newNickname);
		return TCPClient.send(command);
	}

	public static Result changePassword(String newPassword, String oldPassword) {
		String command = UserMenusCommands.CHANGE_PASSWORD.getPattern();
		command = command.replace("(?<newPassword>\\S+)", newPassword);
		command = command.replace("(?<oldPassword>\\S+)", oldPassword);
		return TCPClient.send(command);
	}

	public static Result changeEmail(String email) {
		String command = UserMenusCommands.CHANGE_EMAIL.getPattern();
		command = command.replace("(?<email>\\S+)", email);
		return TCPClient.send(command);
	}


	public static Result goToInfoMenu() {
		String command = UserMenusCommands.ENTER_USER_INFO.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientInfoMenu());
		return result;
	}

	public static Result showGameHistory(int number) {
		String command = UserMenusCommands.GAME_HISTORY.getPattern();
		command = command.replace("( -n (?<numberOfGames>\\d+))?", number == 0 ? "" : " -n "+ String.valueOf(number));
		return TCPClient.send(command);
	}

	public static Result exit() {
		String command = UserMenusCommands.EXIT.getPattern();
		Result result = TCPClient.send(command);
		if (ClientAppview.getMenu() instanceof ClientInfoMenu) ClientAppview.setMenu(new ClientProfileMenu());
		else if (ClientAppview.getMenu() instanceof ClientProfileMenu) ClientAppview.setMenu(new ClientMainMenu());
		else if (ClientAppview.getMenu() instanceof ClientSocialMenu) {
			ClientAppview.setMenu(new ClientMainMenu());
			stopUpdatingFriendList();
		} else if (ClientAppview.getMenu() instanceof ClientHistoryMenu) ClientAppview.setMenu(new ClientInfoMenu());
		else if (ClientAppview.getMenu() instanceof ClientRankingMenu) {
			ClientAppview.setMenu(new ClientMainMenu());
			stopUpdatingLeaderboard();
		}
		return result;
	}

	public static Result saveChanges(String username, String nickname, String email, String oldPassword, String newPassword, String confirmNewPassword) {
		String command = UserMenusCommands.SAVE_CHANGES.getPattern();
		command = command.replace("(?<username>\\S+)", username);
		command = command.replace("(?<nickname>\\S+)", nickname);
		command = command.replace("(?<email>\\S+)", email);
		command = command.replace("(?<oldPassword>\\S+)", oldPassword);
		command = command.replace("(?<newPassword>\\S+)", newPassword);
		command = command.replace("(?<confirmNewPassword>\\\\S+)", confirmNewPassword);
		return TCPClient.send(command);
	}

	public static String getUsername() {
		String command = UserMenusCommands.GET_USERNAME.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getNickname() {
		String command = UserMenusCommands.GET_NICKNAME.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getEmail() {
		String command = UserMenusCommands.GET_EMAIL.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getMaxRating() {
		String command = UserMenusCommands.GET_MAX_RATING.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getRank() {
		String command = UserMenusCommands.GET_RANK.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getNumberOfPlayedMatches() {
		String command = UserMenusCommands.GET_PLAYED_MATCHES.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getNumberOfWins() {
		String command = UserMenusCommands.GET_WINS.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getNumberOfDraws() {
		String command = UserMenusCommands.GET_DRAWS.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static String getNumberOfLosses() {
		String command = UserMenusCommands.GET_LOSSES.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}

	public static Result showFriends() {
		String command = UserMenusCommands.SHOW_FRIENDS.getPattern();
		return TCPClient.send(command);
	}

	public static Result showReceivedFriendRequests() {
		String command = UserMenusCommands.SHOW_RECEIVED_FRIEND_REQUESTS.getPattern();
		return TCPClient.send(command);
	}

	public static Result showSentFriendRequests() {
		String command = UserMenusCommands.SHOW_SENT_FRIEND_REQUESTS.getPattern();
		return TCPClient.send(command);
	}

	public static Result acceptFriendRequest(String username) {
		String command = UserMenusCommands.ACCEPT_FRIEND_REQUEST.getPattern();
		command = command.replace("(?<username>.*)", username);
		return TCPClient.send(command);
	}

	public static Result declineFriendRequest(String username) {
		String command = UserMenusCommands.DECLINE_FRIEND_REQUEST.getPattern();
		command = command.replace("(?<username>.*)", username);
		return TCPClient.send(command);
	}

	public static Result removeFriend(String username) {
		String command = UserMenusCommands.REMOVE_FRIEND.getPattern();
		command = command.replace("(?<username>.*)", username);
		return TCPClient.send(command);
	}

	public static Result sendFriendRequest(String username) {
		String command = UserMenusCommands.SEND_FRIEND_REQUEST.getPattern();
		System.out.println(username);
		command = command.replace("(?<username>.*)", username);
		return TCPClient.send(command);
	}

	public static Result unsendFriendRequest(String username) {
		String command = UserMenusCommands.UNSEND_FRIEND_REQUEST.getPattern();
		command = command.replace("(?<username>.*)", username);
		return TCPClient.send(command);
	}

	public static ArrayList<String> showPlayersInfo() {
		String command = UserMenusCommands.SHOW_PLAYERS_INFO.getPattern();
		Result result = TCPClient.send(command);
		if (result == null) return null;
		String[] players = result.getMessage().split("\n");
		return new ArrayList<>(Arrays.asList(players));
	}

	public static void startUpdatingFriendList(ClientSocialMenu menu) {
		if (friendListUpdater != null) stopUpdatingFriendList();
		friendListUpdater = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
					return;
				}
				javafx.application.Platform.runLater(menu::updateFriendsList);
			}
		});
		friendListUpdater.setDaemon(true);
		friendListUpdater.start();
	}

	private static void stopUpdatingFriendList() {
		friendListUpdater.interrupt();
		friendListUpdater = null;
	}

	public static void startUpdatingLeaderboard(ClientRankingMenu menu) {
		if (leaderboardUpdater != null) stopUpdatingLeaderboard();
		leaderboardUpdater = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
					return;
				}
				javafx.application.Platform.runLater(menu::updateScreen);
			}
		});
		leaderboardUpdater.setDaemon(true);
		leaderboardUpdater.start();
	}

	private static void stopUpdatingLeaderboard() {
		leaderboardUpdater.interrupt();
		leaderboardUpdater = null;
	}

	public static Result getPage(int page) {
		String command = UserMenusCommands.SHOW_PAGE_INFO.getPattern();
		command = command.replace("(?<pageNumber>\\d+)", String.valueOf(page));
		return TCPClient.send(command);
	}

	public static Result getPageCount() {
		String command = UserMenusCommands.GET_PAGE_COUNT.getPattern();
		return TCPClient.send(command);
	}

}
