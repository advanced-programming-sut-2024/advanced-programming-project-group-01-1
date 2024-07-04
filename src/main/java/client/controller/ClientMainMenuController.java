package client.controller;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.game.prematch.ClientMatchFinderMenu;
import client.view.sign.login.ClientLoginMenu;
import client.view.user.ClientProfileMenu;
import message.MainMenuCommands;
import message.Result;

import java.util.Objects;

public class ClientMainMenuController {

	public static Result logout() {
		String command = MainMenuCommands.LOGOUT.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientLoginMenu());
		return result;
	}

	public static Result goToProfileMenu() {
		String command = MainMenuCommands.ENTER_PROFILE_MENU.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientProfileMenu());
		return result;
	}

	public static Result goToMatchFinderMenu() {
		String command = MainMenuCommands.ENTER_GAME_MENU.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientMatchFinderMenu());
		return result;
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static String getLoggedInUsername() {
		String command = MainMenuCommands.GET_LOGGED_IN_USERNAME.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}
}
