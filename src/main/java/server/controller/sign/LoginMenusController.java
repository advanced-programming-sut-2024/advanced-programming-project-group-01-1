package server.controller.sign;

import server.controller.enums.Validation;
import message.Result;
import server.model.Client;
import server.model.user.User;
import server.view.MainMenu;
import server.view.sign.login.ForgotPasswordMenu;
import server.view.sign.login.LoginMenu;
import server.view.sign.login.SetPasswordMenu;
import server.view.sign.register.RegisterMenu;

public class LoginMenusController {

	public static Result login(Client client, String username, String password, boolean stayLoggedIn) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		if (!user.getPassword().equals(password)) return new Result("Password is incorrect", false);
		User.setLoggedInUser(user);
	//	Appview.setMenu(new MainMenu());
		return new Result("Login successful", true);
	}

	public static Result forgotPassword(Client client, String username) {
		User user = User.getUserByUsername(username);
		if (user == null) return new Result("Username doesn't exist", false);
		User.setLoggedInUser(user);
	//	Appview.setMenu(new ForgotPasswordMenu());
		return new Result(user.getQuestion().getQuestion(), true);
	}

	public static Result answerQuestion(Client client, String answer) {
		User user = User.getLoggedInUser();

		if (user.getQuestion().isAnswerCorrect(answer)) {
			//Appview.setMenu(new SetPasswordMenu());
			return new Result("Answer is correct\nSet a new password", true);
		}
		return new Result("Answer is incorrect", false);
	}

	public static Result setPassword(Client client, String password) {
		if (!Validation.STRONG_PASSWORD.matches(password)) return new Result("Password is weak", false);
		User user = User.getLoggedInUser();
		user.setPassword(password.equals("random") ? User.generateRandomPassword() : password);
		//Appview.setMenu(new LoginMenu());
		return new Result("Password set successfully", true);
	}

	public static Result exit(Client client) {
		System.exit(0);
		return new Result("Exiting", true);
	}

	public static Result goToRegisterMenu(Client client) {
		User.setLoggedInUser(null);
	//	Appview.setMenu(new RegisterMenu());
		return new Result("Going to register menu", true);
	}

    public static String getQuestion(Client client) {
		return User.getLoggedInUser().getQuestion().getQuestion();
    }
}
