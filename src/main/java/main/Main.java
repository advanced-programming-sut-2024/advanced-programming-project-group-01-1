package main;

import controller.JsonController;
import view.Appview;
import view.Terminal;
import view.sign.login.LoginMenu;
import view.sign.register.RegisterMenu;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						JsonController.save();
					}
				}, "Shutdown-thread"));
		JsonController.load();
		Terminal terminal = new Terminal();
		terminal.setDaemon(true);
		terminal.start();
		Appview.setMenu(new LoginMenu());
	}
}