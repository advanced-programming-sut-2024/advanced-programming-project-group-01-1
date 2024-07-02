package controller;

import model.user.User;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonController {
	private static final String USERS_FILE = "/data/users.json";

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

	public static void load() {
		loadUsers();
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
