package model;

public class User {

	private String username;
	private String nickname;
	private String password;
	private String email;
	private Question question;

	public User(String username, String nickname, String password, String email, Question question) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.question = question;
	}

	public static User getCurrentUser() { //TODO
		return null;
	}

	public static User getUserByUsername() { //TODO
		return null;
	}

}
