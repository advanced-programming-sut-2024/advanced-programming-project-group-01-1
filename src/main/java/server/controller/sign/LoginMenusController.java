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
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) return new Result("Username doesn't exist", false);
			if (!user.getPassword().equals(password)) return new Result("Password is incorrect", false);
			client.setIdentity(user);
			client.setMenu(new MainMenu());
			return new Result("Login successful", true);
		}
	}

	public static Result forgotPassword(Client client, String username) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) return new Result("Username doesn't exist", false);
			client.setIdentity(user);
			client.setMenu(new ForgotPasswordMenu());
			return new Result(user.getQuestion().getQuestion(), true);
		}
	}

	public static Result answerQuestion(Client client, String answer) {
		synchronized (client.getIdentity()) {
			if (client.getIdentity().getQuestion().isAnswerCorrect(answer)) {
				client.setMenu(new SetPasswordMenu());
				return new Result("Answer is correct\nSet a new password", true);
			}
			return new Result("Answer is incorrect", false);
		}
	}

	public static Result setPassword(Client client, String password) {
		if (!Validation.STRONG_PASSWORD.matches(password)) return new Result("Password is weak", false);
		synchronized (client.getIdentity()) {
			client.getIdentity().setPassword(password.equals("random") ? User.generateRandomPassword() : password);
			client.setMenu(new LoginMenu());
			return new Result("Password set successfully", true);
		}
	}

	public static Result exit(Client client) {
		Client.remove(client);
		return new Result("Exiting", true);
	}

	public static Result goToRegisterMenu(Client client) {
		client.setIdentity(null);
		client.setMenu(new RegisterMenu());
		return new Result("Going to register menu", true);
	}

    public static Result getQuestion(Client client) {
		synchronized (client.getIdentity()) {
			return new Result(client.getIdentity().getQuestion().getQuestion(), true);
		}
    }
}
