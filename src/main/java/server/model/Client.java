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
	private boolean isWaiting;
	private boolean isInGame;

	public Client() {
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < 5 || clients.containsKey(tmp.toString()); i++)
			tmp.append((char) (random.nextInt(26) + (random.nextBoolean() ? 'A' : 'a')));
		token = tmp.toString();
		System.out.println("baba bikhial " + token);
		Menu = new LoginMenu();
		identity = null;
		isWaiting = false;
		clients.put(token, this);
	}

	public static Client getClient(String token) {
		return clients.get(token);
	}

	public static Client getClient(User user) {
		for (Client client : clients.values()) {
			if (client.getIdentity() == user) return client;
		}
		return null;
	}

	public static void remove(Client client) {
		if (client.getIdentity() != null) {
			User.getOnlineUsers().remove(client.getIdentity());
			if (client.getIdentity().getChallengedUser() != null) {
				client.getIdentity().getChallengedUser().getMatchRequests().remove(client.getIdentity());
				client.getIdentity().setChallengedUser(null);
			}
		}
		clients.remove(client.getToken());
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

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean waiting) {
		isWaiting = waiting;
	}

	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean inGame) {
		isInGame = inGame;
	}
}
