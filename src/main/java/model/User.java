package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static User loggedInUser = null;
	private static boolean stayLoggedIn = false;
	private static ArrayList<User> users = new ArrayList<>();
	private final int id;
	private String username;
	private String nickname;
	private String password;
	private String email;
	private Question question;

	public User(String username, String nickname, String password, String email, Question question) {
		this.id = User.getNumberOfUsers() + 1;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.question = question;
		this.updateData();
	}

	public static User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public static int getNumberOfUsers() {
		return users.size();
	}

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static ArrayList<User> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<User> users) {
		User.users = users;
	}

	public static void addUser(User user) {
		users.add(user);
	}

	public static void removeUser(User user) {
		users.remove(user);
	}

	public static boolean isStayLoggedIn() {
		return stayLoggedIn;
	}

	public static void setStayLoggedIn(boolean stayLoggedIn) {
		User.stayLoggedIn = stayLoggedIn;
	}

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public Question getQuestion() {
		return this.question;
	}

	public int getMaxScore() {
		// TODO:
		return 0;
	}

	public int getRank() {
		// TODO:
		return 0;
	}

	public int getNumberOfPlayedMatches() {
		// TODO:
		return 0;
	}

	public int getNumberOfWins() {
		// TODO:
		return 0;
	}

	public int getNumberOfDraws() {
		// TODO:
		return 0;
	}

	public int getNumberOfLosses() {
		// TODO:
		return 0;
	}

	private void updateData() {
		// TODO:
	}
}
