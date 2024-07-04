package client.controller.sign;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.sign.login.ClientForgotPasswordMenu;
import client.view.sign.login.ClientLoginMenu;
import client.view.sign.login.ClientSetPasswordMenu;
import client.view.sign.register.ClientRegisterMenu;
import message.LoginMenusCommands;
import message.Result;

import java.util.Objects;

public class ClientLoginMenusController {

	public static Result login(String username, String password, boolean stayLoggedIn) {
		String command = LoginMenusCommands.LOGIN.getPattern();
		command = command.replace("(?<username>\\S+)", username);
		command = command.replace("(?<password>\\S+)", password);
		command = command.replace("(?<stayLoggedIn> -stay-logged-in)?", (stayLoggedIn ? "-stay-logged-in" : ""));
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientMainMenu());
		return result;
	}

	public static Result forgotPassword(String username) {
		String command = LoginMenusCommands.FORGOT_PASSWORD.getPattern();
		command = command.replace("(?<username>\\S+)", username);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientForgotPasswordMenu());
		return result;
	}

	public static Result answerQuestion(String answer) {
		String command = LoginMenusCommands.ANSWER_QUESTION.getPattern();
		command = command.replace("(?<answer>\\S+)", answer);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientSetPasswordMenu());
		return result;
	}

	public static Result setPassword(String password) {
		String command = LoginMenusCommands.SET_PASSWORD.getPattern();
		command = command.replace("(?<password>\\S+)", password);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientLoginMenu());
		return result;
	}

	public static Result exit() {
		String command = LoginMenusCommands.EXIT.getPattern();
		Result result = TCPClient.send(command);
		System.exit(0);
		return result;
	}

	public static Result goToRegisterMenu() {
		String command = LoginMenusCommands.ENTER_REGISTER_MENU.getPattern();
		ClientAppview.setMenu(new ClientRegisterMenu());
		return TCPClient.send(command);
	}

	public static String getQuestion() {
		String command = "get question";
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}
}
