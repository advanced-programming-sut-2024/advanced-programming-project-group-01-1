package main;

import controller.JsonController;
import view.Appview;
import view.Terminal;
import view.sign.login.LoginMenu;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						JsonController.save();
					}
				}, "Shutdown-thread"));
		JsonController.load();
		Appview.setMenu(new LoginMenu());
		//TODO: run the graphical interface
		Terminal terminal = new Terminal();
		terminal.run();
	}
}