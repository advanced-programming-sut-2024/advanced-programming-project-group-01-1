package controller.game;

import main.CardCreator;
import model.Result;
import model.card.Card;
import model.game.Faction;
import model.game.Game;
import model.leader.Leader;
import model.user.Deck;
import model.user.User;

import java.io.*;

public class PreMatchMenusController {

	private static User opponent = null;

	public static Result createGame(String opponentUsername) {
		User opponent = User.getUserByUsername(opponentUsername);
		if (opponent == null) return new Result("User Not Found", false);
		if (opponent.equals(User.getLoggedInUser())) return new Result("You Cannot Play With Yourself", false);
		PreMatchMenusController.opponent = opponent;
		return new Result("Entering Lobby", true);
	}

	public static Result showFactions() {
		StringBuilder message = new StringBuilder();
		for (Faction faction : Faction.values()) {
			message.append(faction).append("\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result selectFaction(String factionName) {
		Faction faction = Faction.getFaction(factionName);
		if (faction == null) return new Result("Invalid Faction Name", false);
		User.getLoggedInUser().setDeck(new Deck(faction));
		return new Result("Faction Selected Successfully", true);
	}

	public static Result showCards() {
		StringBuilder message = new StringBuilder();
		for (Card card : User.getLoggedInUser().getDeck().getAvailableCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showDeck() {
		StringBuilder message = new StringBuilder();
		for (Card card : User.getLoggedInUser().getDeck().getCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showInfo() {
		StringBuilder message = new StringBuilder();
		message.append("Username: ").append(User.getLoggedInUser().getUsername()).append("\n");
		message.append("Faction: ").append(User.getLoggedInUser().getDeck().getFaction()).append("\n");
		message.append("Leader: ").append(User.getLoggedInUser().getDeck().getLeader()).append("\n");
		message.append("Number of Cards in Deck: ").append(User.getLoggedInUser().getDeck().getCards().size()).append("\n");
		message.append("Number of Special Cards in Deck: ").append(User.getLoggedInUser().getDeck().getSpecialCount()).append("\n");
		message.append("Number of Unit Cards in Deck: ").append(User.getLoggedInUser().getDeck().getUnitCount()).append("\n");
		message.append("Number of Hero Cards in Deck: ").append(User.getLoggedInUser().getDeck().getHeroCount()).append("\n");
		message.append("Total Power of Deck: ").append(User.getLoggedInUser().getDeck().getTotalPower()).append("\n");
		return new Result(message.toString(), true);
	}

//	public static void main(String[] args) {
//		Deck test = new Deck(Faction.MONSTERS);
//		test.setLeader(test.getAvailableLeaders().get(3));
//		test.add(CardCreator.getCard("Leshen"));
//		test.add(CardCreator.getCard("Skellige Storm"));
//		test.add(CardCreator.getCard("Gaunter O'Dimm"));
//		test.add(CardCreator.getCard("Gaunter O'Dimm; Darkness"));
//		test.add(CardCreator.getCard("Gaunter O'Dimm; Darkness"));
//		test.add(CardCreator.getCard("Gaunter O'Dimm; Darkness"));
//		test.add(CardCreator.getCard("Geralt of Rivia"));
//		test.add(CardCreator.getCard("Arachas"));
//		test.add(CardCreator.getCard("Cow"));
//		User user = new User("test", "test", "test", "test", null);
//		user.setDeck(test);
//		User.setLoggedInUser(user);
//		saveDeckByAddress("/C:/Users/S2/Desktop/test.json");
//		User user2 = new User("test2", "test2", "test2", "test2", null);
//		User.setLoggedInUser(user2);
//		loadDeckByAddress("/C:/Users/S2/Desktop/test.json");
//		System.out.println(User.getLoggedInUser().getDeck().getHeroCount());
//	}

	public static Result saveDeckByAddress(String address) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(User.getLoggedInUser().getDeck());
			oos.close();
		} catch (IOException e) {
			return new Result("Invalid address", false);
		}
		return new Result("Deck Saved Successfully", true);
	}

	public static Result saveDeckByName(String name) {
		String path = PreMatchMenusController.class.getResource("/decks/" + name + ".json").getPath();
		return saveDeckByAddress(path);
	}

	public static Result loadDeckByAddress(String address) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
			Deck deck = (Deck) ois.readObject();
			User.getLoggedInUser().setDeck(deck);
		} catch (IOException e) {
			return new Result("Invalid address", false);
		} catch (ClassNotFoundException e) {
			return new Result("Invalid Deck", false);
		}
		return new Result("Deck Loaded Successfully", true);
	}

	public static Result loadDeckByName(String name) {
		String path = PreMatchMenusController.class.getResource("/decks/" + name + ".json").getPath();
		return loadDeckByAddress(path);
	}

	public static Result showLeaders() {
		StringBuilder message = new StringBuilder();
		for (Leader leader : User.getLoggedInUser().getDeck().getAvailableLeaders())
			message.append(leader + "\n");
		return new Result(message.toString(), true);
	}

	public static Result selectLeader(int leaderNumber) {
		if (leaderNumber < 0 || leaderNumber >= User.getLoggedInUser().getDeck().getAvailableLeaders().size())
			return new Result("Invalid Leader Number", false);
		User.getLoggedInUser().getDeck().setLeader(User.getLoggedInUser().getDeck().getAvailableLeaders().get(leaderNumber));
		return new Result("Leader Selected Successfully", true);
	}

	public static Result addToDeck(String cardName, int count) {
		if (count < 1) return new Result("Invalid Count", false);
		Card card = CardCreator.getCard(cardName);
		if (User.getLoggedInUser().getDeck().getAvailableCount(card) < count)
			return new Result("Not Enough Cards Available", false);
		for (int i = 0; i < count; i++)
			User.getLoggedInUser().getDeck().add(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Added Successfully", true);
	}

	public static Result deleteFromDeck(int cardNumber, int count) {
		if (cardNumber < 0 || cardNumber >= User.getLoggedInUser().getDeck().getCards().size())
			return new Result("Invalid Card Number", false);
		Card card = User.getLoggedInUser().getDeck().getCards().get(cardNumber);
		if (count < 1) return new Result("Invalid Count", false);
		if (User.getLoggedInUser().getDeck().getInDeckCount(card) < count)
			return new Result("Not Enough Cards in Deck", false);
		for (int i = 0; i < count; i++)
			User.getLoggedInUser().getDeck().getCards().remove(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Removed Successfully", true);
	}

	public static Result changeTurn() {
		User temp = User.getLoggedInUser();
		User.setLoggedInUser(opponent);
		opponent = temp;
		return new Result("Turn Changed Successfully", true);
	}

	public static Result startGame() {
		if (!User.getLoggedInUser().getDeck().isValid()) return new Result("Your Deck Is Invalid", false);
		if (!opponent.getDeck().isValid()) return new Result("Opponent's Deck Is Invalid", false);
		changeTurn();
		Game.createGame(User.getLoggedInUser(), opponent);
		return new Result("Game Started Successfully", true);
	}

}
