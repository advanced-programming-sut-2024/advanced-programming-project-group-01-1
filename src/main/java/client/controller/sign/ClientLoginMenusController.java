package client.controller.sign;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.MainMenu;
import client.view.sign.login.ForgotPasswordMenu;
import client.view.sign.login.LoginMenu;
import client.view.sign.login.SetPasswordMenu;
import client.view.sign.register.RegisterMenu;
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
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new MainMenu());
		return result;
	}

	public static Result forgotPassword(String username) {
		String command = LoginMenusCommands.FORGOT_PASSWORD.getPattern();
		command = command.replace("(?<username>\\S+)", username);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ForgotPasswordMenu());
		return result;
	}

	public static Result answerQuestion(String answer) {
		String command = LoginMenusCommands.ANSWER_QUESTION.getPattern();
		command = command.replace("(?<answer>\\S+)", answer);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new SetPasswordMenu());
		return result;
	}

	public static Result setPassword(String password) {
		String command = LoginMenusCommands.SET_PASSWORD.getPattern();
		command = command.replace("(?<password>\\S+)", password);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new LoginMenu());
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
		ClientAppview.setMenu(new RegisterMenu());
		return TCPClient.send(command);
	}

	public static String getQuestion() {
		String command = "get question";
		return Objects.requireNonNull(TCPClient.send(command)).getMessage();
	}
}
