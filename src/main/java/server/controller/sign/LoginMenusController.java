package server.controller.sign;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import message.Result;
import server.controller.enums.Validation;
import server.model.Client;
import server.model.user.User;
import server.view.MainMenu;
import server.view.sign.login.AuthenticationMenu;
import server.view.sign.login.ForgotPasswordMenu;
import server.view.sign.login.LoginMenu;
import server.view.sign.login.SetPasswordMenu;
import server.view.sign.register.RegisterMenu;

import jakarta.mail.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class LoginMenusController {

	private static final Random random = new Random();
	private static final Map<Client, String> loggingInClients = new HashMap<>();

	public static Result login(Client client, String username, String password, boolean stayLoggedIn) {
		synchronized (User.getUsers()) {
			User user = User.getUserByUsername(username);
			if (user == null) return new Result("Username doesn't exist", false);
			if (!user.getPassword().equals(password)) return new Result("Password is incorrect", false);
			client.setIdentity(user);
			client.setMenu(new AuthenticationMenu());
			return new Result("Enter authentication code to login", true);
		}
	}

	public static Result sendEmail(Client client) {
		/*final String username = "mohamed.mahdi.khavari@gmail.com";
		final String password = "iqupkzowfhlbsvsc";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mohamed.mahdi.khavari@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(client.getIdentity().getEmail()));
			message.setSubject("Testing Gmail SSL");
			message.setText("Dear Mail Crawler,"
					+ "\n\n Please do not spam my email!");
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("email not sent", false);
		}
		return new Result("email sent", true);*/
		loggingInClients.put(client, "123456");
		return new Result("email sent", true);
	}



	public static Result checkCode(Client client, String code) {
		if (loggingInClients.containsKey(client) && loggingInClients.get(client).equals(code)) {
			client.setMenu(new MainMenu());
			return new Result("Code is correct", true);
		}
		return new Result("Code is incorrect", false);
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
		if (client.getMenu() instanceof AuthenticationMenu) {
			client.setIdentity(null);
			client.setMenu(new LoginMenu());
			loggingInClients.remove(client);
			return new Result("Exiting", true);
		}
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
