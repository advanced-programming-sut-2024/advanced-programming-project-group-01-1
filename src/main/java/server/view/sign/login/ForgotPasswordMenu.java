package server.view.sign.login;

import message.Command;
import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class ForgotPasswordMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.ANSWER_QUESTION.getMatcher(command.getCommand())) != null)
			result = answerQuestion(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command.getCommand())) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Forgot Password Menu", true);
	}

	private Result answerQuestion(Matcher matcher) {
		String answer = matcher.group("answer");
		return LoginMenusController.answerQuestion(answer);
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}

}
