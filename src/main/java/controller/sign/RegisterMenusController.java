package controller.sign;

import controller.enums.RegisterMenusResponses;
import controller.enums.Validation;
import model.Result;
import model.user.Question;
import model.user.User;
import view.Appview;
import view.sign.login.LoginMenu;

public class RegisterMenusController {

	private static User registeringUser;

	public static Result register(String username, String password, String passwordConfirm, String nickname, String email) {
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
			return RegisterMenusResponses.REGISTER_SUCCESSFUL.getResult();
		}
	}

	public static Result pickQuestion(int questionNumber, String answer, String answerConfirm) {
		if (questionNumber < 0 || questionNumber >= Question.questions.length)
			return RegisterMenusResponses.INVALID_QUESTION_NUMBER.getResult();
		else if (!answer.equals(answerConfirm)) return new Result("Answers don't match", false);
		else {
			registeringUser.setQuestion(new Question(Question.questions[questionNumber], answer));
			return new Result("Question picked successfully", true);
		}
	}

	public static Result exit() {
		Appview.setMenu(new LoginMenu());
		return new Result("Exiting Register Menu", true);
	}

	public static String[] getQuestions(){
		return Question.questions;
	}
}
