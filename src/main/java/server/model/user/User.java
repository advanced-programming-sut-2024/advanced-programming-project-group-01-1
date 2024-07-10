package server.model.user;

import server.controller.enums.Validation;
import server.model.GameInfo;
import server.model.game.Faction;
import server.model.game.Game;
import server.model.tournament.Bracket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class User implements Serializable {

	private static ArrayList<User> users = new ArrayList<>();
	private static ArrayList<User> onlineUsers = new ArrayList<>();
	private final int id;
	private String username;
	private String nickname;
	private String password;
	private String email;
	private boolean isEmailVerified = false;
	private Question question;
	private Deck deck;
	private transient Game currentGame;
	private transient Bracket currentBracket;
	private final ArrayList<GameInfo> history = new ArrayList<>();
	private final ArrayList<User> sentRequests = new ArrayList<>(), receivedRequests = new ArrayList<>();
	private final ArrayList<User> matchRequests = new ArrayList<>();
	private User challengedUser;
	private double elo = 1000, maxElo = 1000;

	public User(String username, String nickname, String password, String email, Question question) {
		this.id = users.size() + 1;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.question = question;
		this.deck = new Deck(Faction.NORTHERN_REALMS);
		users.add(this);
	}

	public static User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public static User getUserById(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public static ArrayList<User> getUsers() {
		return users;
	}

	public static ArrayList<User> getOnlineUsers() {
		return onlineUsers;
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

	public static String generateRandomPassword(){
		ArrayList<Character> characters = new ArrayList<>();
		for (char c = 'a'; c <= 'z'; c++) characters.add(c);
		for (char c = 'A'; c <= 'Z'; c++) characters.add(c);
		for (char c = '0'; c <= '9'; c++) characters.add(c);
		characters.add('!'); characters.add('@'); characters.add('#'); characters.add('$');
		characters.add('%'); characters.add('^'); characters.add('&'); characters.add('*');
		Random random = new Random();
		StringBuilder password = new StringBuilder();
		int length = 8 + random.nextInt(8);
		for (int i = 0; i < length; i++) {
			password.append(characters.get(random.nextInt(characters.size())));
		}
		if (!Validation.STRONG_PASSWORD.matches(password.toString())) return generateRandomPassword();
		return password.toString();
	}

	public static double calculateElo(double elo1, double elo2, int winnerId) {
		double expected1 = 1 / (1 + Math.pow(10, (elo2 - elo1) / 400.0));
		int k = 32;
		if (elo1 < 2100) k = 64;
		if (elo1 < 1600) k = 128;
		if (winnerId == 0) {
			elo1 += (int) Math.round(k * (0.5 - expected1));
		} else if (winnerId == 1) {
			elo1 += (int) Math.round(k * (1 - expected1));
		} else {
			elo1 += (int) Math.round(k * (0 - expected1));
		}
		return elo1;
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

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		isEmailVerified = emailVerified;
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

	public int getMaxElo() {
		return (int) maxElo;
	}

	public double getElo() {
		return this.elo;
	}

	public void setElo(double elo) {
		this.elo = elo;
		maxElo = Double.max(maxElo, elo);
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

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public Bracket getCurrentBracket() {
		return currentBracket;
	}

	public void setCurrentBracket(Bracket currentBracket) {
		this.currentBracket = currentBracket;
	}

	public ArrayList<User> getFriends() {
		ArrayList<User> friends = new ArrayList<>(this.sentRequests);
		friends.retainAll(this.receivedRequests);
		return friends;
	}

	public ArrayList<User> getSentRequests() {
		ArrayList<User> sentRequests = new ArrayList<>(this.sentRequests);
		sentRequests.removeAll(this.receivedRequests);
		return sentRequests;
	}

	public ArrayList<User> getReceivedRequests() {
		ArrayList<User> receivedRequests = new ArrayList<>(this.receivedRequests);
		receivedRequests.removeAll(this.sentRequests);
		return receivedRequests;
	}

	public ArrayList<User> getMatchRequests() {
		return matchRequests;
	}

	public User getChallengedUser() {
		return challengedUser;
	}

	public void setChallengedUser(User challengedUser) {
		this.challengedUser = challengedUser;
	}

	public void addSentRequest(User user) {
		this.sentRequests.add(user);
	}

	public void removeSentRequest(User user) {
		this.sentRequests.remove(user);
	}

	public void addReceivedRequest(User user) {
		this.receivedRequests.add(user);
	}

	public void removeReceivedRequest(User user) {
		this.receivedRequests.remove(user);
	}

}
