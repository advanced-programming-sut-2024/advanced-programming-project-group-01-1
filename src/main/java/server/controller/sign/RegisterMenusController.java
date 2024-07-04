package server.controller.sign;

import message.Result;
import server.controller.enums.RegisterMenusResponses;
import server.controller.enums.Validation;
import server.model.Client;
import server.model.user.Question;
import server.model.user.User;
import server.view.sign.login.LoginMenu;
import server.view.sign.register.PickQuestionMenu;

public class RegisterMenusController {

	public static Result register(Client client, String username, String password, String passwordConfirm, String nickname, String email) {
		synchronized (User.getUsers()) {
			if (User.getUserByUsername(username) != null) return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
			else if (!Validation.USERNAME.matches(username)) return RegisterMenusResponses.INVALID_USERNAME.getResult();
			else if (!Validation.EMAIL.matches(email)) return RegisterMenusResponses.INVALID_EMAIL.getResult();
			else if (!Validation.CORRECT_PASSWORD.matches(password))
				return RegisterMenusResponses.INVALID_PASSWORD.getResult();
			else if (!Validation.STRONG_PASSWORD.matches(password))
				return RegisterMenusResponses.WEAK_PASSWORD.getResult();
			else if (!password.equals(passwordConfirm)) return RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult();
			else {
				client.setIdentity(new User(username, nickname, password, email, null));
				client.setMenu(new PickQuestionMenu());
				return RegisterMenusResponses.REGISTER_SUCCESSFUL.getResult();
			}
		}
	}

	public static Result pickQuestion(Client client, int questionNumber, String answer, String answerConfirm) {
		if (questionNumber < 0 || questionNumber >= Question.questions.length)
			return RegisterMenusResponses.INVALID_QUESTION_NUMBER.getResult();
		else if (!answer.equals(answerConfirm)) return new Result("Answers don't match", false);
		else {
			client.getIdentity().setQuestion(new Question(Question.questions[questionNumber], answer));
			client.setMenu(new LoginMenu());
			return new Result("Question picked successfully", true);
		}
	}

	public static Result exit(Client client) {
		client.setMenu(new LoginMenu());
		return new Result("Exiting Register Menu", true);
	}

	public static Result getQuestions() {
		StringBuilder questions = new StringBuilder();
		for (String question : Question.questions) {
			questions.append(question).append(",");
		}
		return new Result(questions.toString(), true);
	}
}
