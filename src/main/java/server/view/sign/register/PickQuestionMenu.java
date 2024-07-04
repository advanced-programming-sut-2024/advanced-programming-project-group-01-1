package server.view.sign.register;

import message.Command;
import message.RegisterMenusCommands;
import message.Result;
import server.controller.sign.RegisterMenusController;
import server.view.Menuable;
import server.view.sign.login.LoginMenu;

import java.util.regex.Matcher;

public class PickQuestionMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.PICK_QUESTION.getMatcher(command.getCommand())) != null)
			result = pickQuestion(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(command.getCommand())) != null)
			result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}


	private Result pickQuestion(Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
		String answerConfirm = matcher.group("answerConfirm");
		Result result = RegisterMenusController.pickQuestion(questionNumber, answer, answerConfirm);
		if (result.isSuccessful()) {
			Appview.setMenu(new LoginMenu());
		}
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Pick Question Menu", true);
	}

	private Result exit(Matcher matcher) {
		return RegisterMenusController.exit();
	}

}
