package controller.sign;

import controller.enums.RegisterMenusResponses;
import controller.enums.Validation;
import model.Result;
import model.User;

public class RegisterMenusController {

	private static User registeringUser;

	public static Result register(String username, String password, String passwordConfirm, String nickname, String email) {
		if (User.getUserByUsername(username) != null) {
			return RegisterMenusResponses.DUPLICATE_USERNAME.getResult();
		} else if (!Validation.USERNAME.matches(username)){
			return RegisterMenusResponses.INVALID_USERNAME.getResult();
		} else if (!Validation.EMAIL.matches(email)){
			return RegisterMenusResponses.INVALID_EMAIL.getResult();
		} else if (!Validation.CORRECT_PASSWORD.matches(password)){
			return RegisterMenusResponses.INVALID_PASSWORD.getResult();
		} else if (!Validation.STRONG_PASSWORD.matches(password)){
			return RegisterMenusResponses.WEAK_PASSWORD.getResult();
		} else if (!password.equals(passwordConfirm)){
			return RegisterMenusResponses.PASSWORDS_DONT_MATCH.getResult();
		} else {
			registeringUser = new User(username, nickname, password, email, null);
			return RegisterMenusResponses.REGISTER_SUCCESSFUL.getResult();
		}
	}

	public static Result pickQuestion(int questionNumber, String answer, String answerConfirm) {
		//TODO
		return null;
	}
}
