package view.sign.login;

import controller.sign.LoginMenusController;
import model.Result;
import view.Appview;
import view.Menuable;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ForgotPasswordMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenuCommands.ANSWER_QUESTION.getMatcher(input)) != null) {
			result = answerQuestion(matcher);
		} else if ((matcher = LoginMenuCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else{
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result answerQuestion(Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
		Result result = LoginMenusController.answerQuestion(questionNumber, answer);
		if (result.isSuccessful()) {
			Appview.setMenu(new SetPasswordMenu());
		}
		return result;
	}

	private Result exit(Matcher matcher) {
		Appview.setMenu(new LoginMenu());
		return null;
	}

}
