package controller;

import model.user.User;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonController {
	private static final String USERS_FILE = "/data/users.json";
	private static final String LOGGED_IN_USER = "/data/loggedInUser.json";

	public static void save() {
		saveUsers();
	}

	private static void saveUsers() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
					Paths.get(JsonController.class.getResource(USERS_FILE).toURI()).toString()));
			oos.writeObject(User.getUsers());
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveLoggedInUser() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
					Paths.get(JsonController.class.getResource(LOGGED_IN_USER).toURI()).toString()));
			if (User.getLoggedInUser() == null) oos.writeObject(null);
			else oos.writeObject(User.getLoggedInUser().getId());
			oos.close();
		} catch (Exception ignored) {
		}
	}

	public static void load() {
		loadUsers();
	}

	public static User loadLoggedInUser() {
		try {
			InputStream is = JsonController.class.getResourceAsStream(LOGGED_IN_USER);
			ObjectInputStream ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			User user;
			if (obj == null) user = null;
			else user = User.getUserById((Integer) obj);
			ois.close();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public static void loadUsers() {
		ArrayList<User> users;
		try {
			InputStream is = JsonController.class.getResourceAsStream(USERS_FILE);
			ObjectInputStream ois = new ObjectInputStream(is);
			try {
				users = (ArrayList<User>) ois.readObject();
			} catch (EOFException e) {
				users = new ArrayList<>();
			}
			ois.close();
			User.setUsers(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
