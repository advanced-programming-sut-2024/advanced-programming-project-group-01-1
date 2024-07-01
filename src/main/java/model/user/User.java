package model.user;

import model.GameInfo;
import model.game.Faction;

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
	private Deck deck;
	private final ArrayList<GameInfo> history;
	private int elo;

	public User(String username, String nickname, String password, String email, Question question) {
		this.id = users.size() + 1;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.question = question;
		this.deck = new Deck(Faction.NORTHERN_REALMS);
		this.history = new ArrayList<>();
		this.elo = 1000;
		users.add(this);
	}

	public static void setLoggedInUser(User loggedInUser) {
		User.loggedInUser = loggedInUser;
	}

	public static User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public ArrayList<GameInfo> getHistory() {
		return history;
	}

	public int getMaxScore() {
		int maxScore = 0;
		for (GameInfo gameInfo : this.history) {
			int score = gameInfo.getMyFinalScore();
			if (score > maxScore) maxScore = score;
		}
		return maxScore;
	}

	public int getElo() {
		return this.elo;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}

	public int getRank() {
		int rank = 1;
		for (User user : users) {
			if (user.elo > this.elo) rank++;
		}
		return rank;
	}

	public int getNumberOfPlayedMatches() {
		return this.history.size();
	}

	public int getNumberOfWins() {
		int wins = 0;
		for (GameInfo gameInfo : this.history) {
			if (gameInfo.getWinnerId() == this.id) wins++;
		}
		return wins;
	}

	public int getNumberOfDraws() {
		int draws = 0;
		for (GameInfo gameInfo : this.history) {
			if (gameInfo.getWinnerId() == 0) draws++;
		}
		return draws;
	}

	public int getNumberOfLosses() {
		int losses = 0;
		for (GameInfo gameInfo : this.history) {
			if (gameInfo.getWinnerId() != this.id && gameInfo.getWinnerId() != 0) losses++;
		}
		return losses;
	}

	public Deck getDeck() {
		return this.deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

}
