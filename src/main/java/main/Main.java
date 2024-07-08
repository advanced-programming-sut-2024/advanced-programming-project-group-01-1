package main;

import controller.JsonController;
import model.user.User;
import view.Appview;
import view.MainMenu;
import view.Terminal;
import view.sign.login.LoginMenu;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(JsonController::save, "Shutdown-thread"));
		JsonController.load();
		Terminal terminal = new Terminal();
		terminal.setDaemon(true);
		terminal.start();
		User user = JsonController.loadLoggedInUser();
		if (user != null) {
			User.setLoggedInUser(user);
			Appview.setMenu(new MainMenu());
		} else Appview.setMenu(new LoginMenu());
	}
}