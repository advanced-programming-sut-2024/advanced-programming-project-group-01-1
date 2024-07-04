package client.main;

import client.view.ClientAppview;
import client.view.Terminal;
import client.view.sign.login.ClientLoginMenu;

public class ClientMain {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> TCPClient.send("remove token")));
		Terminal terminal = new Terminal();
		terminal.setDaemon(true);
		terminal.start();
		ClientAppview.setMenu(new ClientLoginMenu());
	}
}