package controller.sign;

import controller.enums.Validation;
import model.Result;
import model.user.Question;
import model.user.User;
import view.Appview;
import view.sign.register.RegisterMenu;

public class LoginMenusController {

	public static Result login(String username, String password, boolean stayLoggedIn) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		if (!user.getPassword().equals(password)) return new Result("Password is incorrect", false);
		User.setLoggedInUser(user);
		return new Result("Login successful", true);
	}

	public static Result forgotPassword(String username) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		User.setLoggedInUser(user);
		return new Result(user.getQuestion().getQuestion(), true);
	}

	public static Result answerQuestion(int questionNumber, String answer) {
		User user = User.getLoggedInUser();
		if (!user.getQuestion().getQuestion().equals(Question.questions[questionNumber]))
			return new Result("This question is not answered", true);
		if (user.getQuestion().isAnswerCorrect(answer))
			return new Result("Answer is correct\nSet a new password", true);
		return new Result("Answer is incorrect", false);
	}

	public static Result setPassword(String password) {
		if (!Validation.STRONG_PASSWORD.matches(password)) return new Result("Password is weak", false);
		User user = User.getLoggedInUser();
		user.setPassword(password.equals("random") ? User.generateRandomPassword() : password);
		return new Result("Password set successfully", true);
	}

	public static Result exit() {
		System.exit(0);
		return new Result("Exiting", true);
	}

	public static Result goToRegisterMenu() {
		User.setLoggedInUser(null);
		Appview.setMenu(new RegisterMenu());
		Appview.runMenu();
		return new Result("Going to register menu", true);
	}
}
