package server.view.sign.login;

import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class ForgotPasswordMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.ANSWER_QUESTION.getMatcher(command)) != null)
			result = answerQuestion(client, matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command)) != null) result = exit(client, matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Forgot Password Menu", true);
	}

	private Result answerQuestion(Client client, Matcher matcher) {
		String answer = matcher.group("answer");
		return LoginMenusController.answerQuestion(client, answer);
	}

	private Result exit(Client client, Matcher matcher) {
		return LoginMenusController.exit(client);
	}

}
