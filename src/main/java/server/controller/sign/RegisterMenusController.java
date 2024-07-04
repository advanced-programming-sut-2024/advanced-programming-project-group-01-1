package server.controller.sign;

import server.controller.enums.RegisterMenusResponses;
import server.controller.enums.Validation;
import message.Result;
import server.model.Client;
import server.model.user.Question;
import server.model.user.User;
import server.view.sign.login.LoginMenu;
import server.view.sign.register.PickQuestionMenu;

public class RegisterMenusController {

	private static User registeringUser;

	public static Result register(Client client, String username, String password, String passwordConfirm, String nickname, String email) {
		if (User.getUserByUsername(username) != null) {
			return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
		} else if (!Validation.USERNAME.matches(username)) {
			return RegisterMenusResponses.INVALID_USERNAME.getResult();
		} else if (!Validation.EMAIL.matches(email)) {
			return RegisterMenusResponses.INVALID_EMAIL.getResult();
		} else if (!Validation.CORRECT_PASSWORD.matches(password)) {
			return RegisterMenusResponses.INVALID_PASSWORD.getResult();
		} else if (!Validation.STRONG_PASSWORD.matches(password)) {
			return RegisterMenusResponses.WEAK_PASSWORD.getResult();
		} else if (!password.equals(passwordConfirm)) {
			return RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult();
		} else {
			registeringUser = new User(username, nickname, password, email, null);
			//Appview.setMenu(new PickQuestionMenu());
			return RegisterMenusResponses.REGISTER_SUCCESSFUL.getResult();
		}
	}

	public static Result pickQuestion(Client client, int questionNumber, String answer, String answerConfirm) {
		if (questionNumber < 0 || questionNumber >= Question.questions.length)
			return RegisterMenusResponses.INVALID_QUESTION_NUMBER.getResult();
		else if (!answer.equals(answerConfirm)) return new Result("Answers don't match", false);
		else {
			registeringUser.setQuestion(new Question(Question.questions[questionNumber], answer));
			Appview.setMenu(new LoginMenu());
			return new Result("Question picked successfully", true);
		}
	}

	public static Result exit(Client client) {
		Appview.setMenu(new LoginMenu());
		return new Result("Exiting Register Menu", true);
	}

	public static String[] getQuestions(Client client){
		return Question.questions;
	}
}
