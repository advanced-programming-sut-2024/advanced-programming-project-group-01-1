package client.controller;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.game.prematch.MatchFinderMenu;
import client.view.sign.login.LoginMenu;
import client.view.user.ProfileMenu;
import message.MainMenuCommands;
import message.Result;

import java.util.Objects;

public class ClientMainMenuController {

	public static Result logout() {
		String command = MainMenuCommands.LOGOUT.getPattern();
		ClientAppview.setMenu(new LoginMenu());
		return TCPClient.send(command);
	}

	public static Result goToProfileMenu() {
		String command = MainMenuCommands.ENTER_PROFILE_MENU.getPattern();
		ClientAppview.setMenu(new ProfileMenu());
		return TCPClient.send(command);
	}

	public static Result goToMatchFinderMenu() {
		String command = MainMenuCommands.ENTER_GAME_MENU.getPattern();
		ClientAppview.setMenu(new MatchFinderMenu());
		return TCPClient.send(command);
	}

	public static Result showCurrentMenu() {
		return new Result("Main Menu", true);
	}

	public static String getLoggedInUsername() {
		String command = "get logged in username";
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}
}
