package server.main;

import server.controller.JsonController;
import server.view.Appview;
import server.view.Terminal;
import server.view.sign.login.LoginMenu;

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