package view.sign.login;

import controller.sign.LoginMenusController;
import model.Result;
import view.Appview;
import view.Menuable;

import java.util.regex.Matcher;

public class ForgotPasswordMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.ANSWER_QUESTION.getMatcher(input)) != null) {
			result = answerQuestion(matcher);
		} else if ((matcher = LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		}
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result showCurrentMenu() {
		return new Result("Forgot Password Menu", true);
	}

	private Result answerQuestion(Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
        return LoginMenusController.answerQuestion(questionNumber, answer);
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}

}
