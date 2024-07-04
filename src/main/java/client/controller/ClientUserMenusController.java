package client.controller;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.MainMenu;
import client.view.user.InfoMenu;
import client.view.user.ProfileMenu;
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
		ClientAppview.setMenu(new InfoMenu());
		return TCPClient.send(command);
	}

	public static Result showGameHistory(int number) {
		String command = UserMenusCommands.GAME_HISTORY.getPattern();
		command = command.replace("( -n (?<numberOfGames>\\d+))?", number == -1 ? "" : String.valueOf(number));
		return TCPClient.send(command);
	}

	public static Result exit() {
		String command = UserMenusCommands.EXIT.getPattern();
		if (ClientAppview.getMenu() instanceof InfoMenu) ClientAppview.setMenu(new ProfileMenu());
		else if (ClientAppview.getMenu() instanceof ProfileMenu) ClientAppview.setMenu(new MainMenu());
		return TCPClient.send(command);
	}

	public static Result saveChanges(String username, String nickname, String email, String oldPassword, String newPassword, String confirmNewPassword) {
		String command = "save changes -u " + username + " -n " + nickname + " -e " + email + " -p " + oldPassword +
				" -np " + newPassword + " -cp " + confirmNewPassword;
		return TCPClient.send(command);
	}
}
