package server.model;

import server.model.user.User;
import server.view.Menuable;
import server.view.sign.login.LoginMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
	private static final Random random = new Random();
	private static final Map<String, Client> clients = new HashMap<>();
	private final String token;
	private Menuable Menu;
	private User identity;

	public Client() {
		StringBuilder tmp = new StringBuilder();
		while(clients.containsKey(tmp.toString()))
			tmp.append((char) random.nextInt(128));
		token = tmp.toString();
		Menu = new LoginMenu();
		identity = null;
		clients.put(token, this);
	}

	public String getToken() {
		return token;
	}

	public void setMenu(Menuable menu) {
		this.Menu = menu;
	}

	public Menuable getMenu() {
		return Menu;
	}

	public void setIdentity(User identity) {
		this.identity = identity;
	}

	public User getIdentity() {
		return identity;
	}

	public static Client getClient(String token) {
		return clients.get(token);
	}
}
