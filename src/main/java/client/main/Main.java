package client.main;

import client.view.ClientAppview;
import client.view.Terminal;
import client.view.sign.login.LoginMenu;

public class Main {
	public static void main(String[] args) {
		Terminal terminal = new Terminal();
		terminal.setDaemon(true);
		terminal.start();
		ClientAppview.setMenu(new LoginMenu());
	}
}