package client.controller;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.user.ClientInfoMenu;
import client.view.user.ClientProfileMenu;
import message.Result;
import message.UserMenusCommands;

public class ClientUserMenusController {

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
		ClientAppview.setMenu(new ClientInfoMenu());
		return TCPClient.send(command);
	}

	public static Result showGameHistory(int number) {
		String command = UserMenusCommands.GAME_HISTORY.getPattern();
		command = command.replace("( -n (?<numberOfGames>\\d+))?", number == -1 ? "" : String.valueOf(number));
		return TCPClient.send(command);
	}

	public static Result exit() {
		String command = UserMenusCommands.EXIT.getPattern();
		if (ClientAppview.getMenu() instanceof ClientInfoMenu) ClientAppview.setMenu(new ClientProfileMenu());
		else if (ClientAppview.getMenu() instanceof ClientProfileMenu) ClientAppview.setMenu(new ClientMainMenu());
		return TCPClient.send(command);
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
}
