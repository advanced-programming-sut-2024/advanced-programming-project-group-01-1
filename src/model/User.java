package model;

import java.io.Serializable;

public class User implements Serializable {

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

	public static User getCurrentUser() {
		// TODO:
		return null;
	}

	public static User getUserByUsername() {
		// TODO:
		return null;
	}

	public static int getNumberOfUsers() {
		// TODO:
		return 0;
	}
}
