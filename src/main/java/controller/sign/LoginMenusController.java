package controller.sign;

import controller.JsonController;
import controller.enums.Validation;
import main.Main;
import model.Result;
import model.user.Question;
import model.user.User;
import view.Appview;
import view.MainMenu;
import view.sign.login.ForgotPasswordMenu;
import view.sign.login.LoginMenu;
import view.sign.login.SetPasswordMenu;
import view.sign.register.RegisterMenu;

public class LoginMenusController {

	public static Result login(String username, String password, boolean stayLoggedIn) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		if (!user.getPassword().equals(password)) return new Result("Password is incorrect", false);
		User.setLoggedInUser(user);
		if (stayLoggedIn) JsonController.saveLoggedInUser();
		Appview.setMenu(new MainMenu());
		return new Result("Login successful", true);
	}

	public static Result forgotPassword(String username) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		User.setLoggedInUser(user);
		Appview.setMenu(new ForgotPasswordMenu());
		return new Result(user.getQuestion().getQuestion(), true);
	}

	public static Result answerQuestion(String answer) {
		User user = User.getLoggedInUser();

		if (user.getQuestion().isAnswerCorrect(answer)) {
			Appview.setMenu(new SetPasswordMenu());
			return new Result("Answer is correct\nSet a new password", true);
		}
		return new Result("Answer is incorrect", false);
	}

	public static Result setPassword(String password) {
		if (!Validation.STRONG_PASSWORD.matches(password)) return new Result("Password is weak", false);
		User user = User.getLoggedInUser();
		user.setPassword(password.equals("random") ? User.generateRandomPassword() : password);
		Appview.setMenu(new LoginMenu());
		return new Result("Password set successfully", true);
	}

	public static Result exit() {
		if (Appview.getMenu() instanceof ForgotPasswordMenu) {
			Appview.setMenu(new LoginMenu());
			return new Result("Exiting Forgot Password Menu", true);
		}
		System.exit(0);
		return new Result("Exiting", true);
	}

	public static Result goToRegisterMenu() {
		User.setLoggedInUser(null);
		Appview.setMenu(new RegisterMenu());
		return new Result("Going to register menu", true);
	}

    public static String getQuestion() {
		return User.getLoggedInUser().getQuestion().getQuestion();
    }
}
