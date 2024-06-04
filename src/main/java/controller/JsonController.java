package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonController {
	private static final String USERS_FILE = "/data/users.json";
	public static void save() {
		saveUsers();
	}

	private static void saveUsers() {
		try {
			String path = JsonController.class.getResource(USERS_FILE).getPath();
			FileWriter fileWriter = new FileWriter(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(User.getUsers());
			fileWriter.write(json);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void load() {
		loadUsers();
	}

	public static void loadUsers() {
		try {
			String path = JsonController.class.getResource(USERS_FILE).getPath();
			Gson gson = new Gson();
			String text = new String(Files.readAllBytes(Paths.get(path)));
			ArrayList<User> users = gson.fromJson(text, new TypeToken<List<User>>(){}.getType());
			if (users == null)
				users = new ArrayList<User>();
			User.setUsers(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}