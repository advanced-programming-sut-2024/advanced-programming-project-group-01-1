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
			oos.writeObject(User.getLoggedInUser());
			oos.close();
		} catch (Exception ignored) {
		}
	}

	public static void load() {
		loadUsers();
	}

	public static User loadLoggedInUser() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					Paths.get(JsonController.class.getResource(LOGGED_IN_USER).toURI()).toString()));
			User user = (User) ois.readObject();
			ois.close();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public static void loadUsers() {
		ArrayList<User> users;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					Paths.get(JsonController.class.getResource(USERS_FILE).toURI()).toString()));
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
