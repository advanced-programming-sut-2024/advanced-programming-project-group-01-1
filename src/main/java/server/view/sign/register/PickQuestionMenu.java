package server.view.sign.register;

import message.RegisterMenusCommands;
import message.Result;
import server.controller.sign.RegisterMenusController;
import server.model.Client;
import server.view.Menuable;
import server.view.sign.login.LoginMenu;

import java.util.regex.Matcher;

public class PickQuestionMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.PICK_QUESTION.getMatcher(command)) != null)
			result = pickQuestion(client, matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(command)) != null)
			result = exit(client, matcher);
		else result = new Result("Invalid command", false);
		return result;
	}


	private Result pickQuestion(Client client, Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
		String answerConfirm = matcher.group("answerConfirm");
		return RegisterMenusController.pickQuestion(client, questionNumber, answer, answerConfirm);
	}

	private Result showCurrentMenu() {
		return new Result("Pick Question Menu", true);
	}

	private Result exit(Client client, Matcher matcher) {
		return RegisterMenusController.exit(client);
	}

}
