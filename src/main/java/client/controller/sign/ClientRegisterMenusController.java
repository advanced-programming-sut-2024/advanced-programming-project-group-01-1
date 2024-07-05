package client.controller.sign;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.sign.login.ClientLoginMenu;
import client.view.sign.register.ClientPickQuestionMenu;
import message.RegisterMenusCommands;
import message.Result;

import java.util.Objects;

public class ClientRegisterMenusController {

	public static Result register(String username, String password, String passwordConfirm, String nickname, String email) {
		String command = RegisterMenusCommands.REGISTER.getPattern();
		command = command.replace("(?<username>\\S+)", username);
		command = command.replace("(?<password>\\S+)", password);
		command = command.replace("(?<passwordConfirm>\\S+)", passwordConfirm);
		command = command.replace("(?<nickname>\\S+)", nickname);
		command = command.replace("(?<email>\\S+)", email);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientPickQuestionMenu());
		return result;
	}

	public static Result pickQuestion(int questionNumber, String answer, String answerConfirm) {
		String command = RegisterMenusCommands.PICK_QUESTION.getPattern();
		command = command.replace("(?<questionNumber>\\d+)", String.valueOf(questionNumber));
		command = command.replace("(?<answer>\\S+)", answer);
		command = command.replace("(?<answerConfirm>\\S+)", answerConfirm);
		System.out.println(command);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientLoginMenu());
		return result;
	}

	public static Result exit() {
		String command = RegisterMenusCommands.EXIT.getPattern();
		Result result = TCPClient.send(command);
		ClientAppview.setMenu(new ClientLoginMenu());
		return result;
	}

	public static String[] getQuestions() {
		String command = RegisterMenusCommands.GET_ALL_QUESTIONS.getPattern();
		return Objects.requireNonNull(TCPClient.send(command)).getMessage().split(",");
	}
}
