package server.controller.game;

import message.Result;
import server.main.CardCreator;
import server.model.Client;
import server.model.card.Card;
import server.model.game.Faction;
import server.model.game.Game;
import server.model.leader.Leader;
import server.model.user.Deck;
import server.model.user.User;
import server.view.MainMenu;
import server.view.game.prematch.LobbyMenu;

import java.io.*;
import java.util.ArrayList;

public class PreMatchMenusController {

	private static int cnt = 0;
	private static User opponent = null;

	public static Result createGame(Client client, String opponentUsername) {
		User opponent = User.getUserByUsername(opponentUsername);
		if (opponent == null) return new Result("User Not Found", false);
		if (opponent.equals(User.getLoggedInUser())) return new Result("You Cannot Play With Yourself", false);
		PreMatchMenusController.opponent = opponent;
		Appview.setMenu(new LobbyMenu());
		return new Result("Entering Lobby", true);
	}

	public static Result showFactions(Client client) {
		StringBuilder message = new StringBuilder();
		for (Faction faction : Faction.values()) {
			message.append(faction).append("\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result selectFaction(Client client, String factionName) {
		Faction faction = Faction.getFaction(factionName);
		if (faction == null) return new Result("Invalid Faction Name", false);
		User.getLoggedInUser().setDeck(new Deck(faction));
		return new Result("Faction Selected Successfully", true);
	}

	public static Result showNowFactionToGraphics(Client client) {
		return new Result(User.getLoggedInUser().getDeck().getFaction().getName(), true);
	}

	public static Result showCards(Client client) {
		StringBuilder message = new StringBuilder();
		for (Card card : User.getLoggedInUser().getDeck().getAvailableCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showCardsForGraphic(Client client) {
		Card previousCard = null;
		int count = 0;
		StringBuilder result = new StringBuilder();
		ArrayList<Card> cards = User.getLoggedInUser().getDeck().getAvailableCards();
		cards.sort(Card::compareTo);
		for (Card card : cards) {
			if (previousCard == null) {
				previousCard = card;
				count++;
			} else if (previousCard.toString().equals(card.toString())) {
				count++;
			} else {
				result.append(previousCard.getName()).append(":").append(count).append("\n");
				previousCard = card;
				count = 1;
			}
		}
		if (previousCard != null) {
			result.append(previousCard.getName()).append(":").append(count).append("\n");
		}
		return new Result(result.toString(), true);
	}

	public static Result showDeck(Client client) {
		StringBuilder message = new StringBuilder();
		for (Card card : User.getLoggedInUser().getDeck().getCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showDeckForGraphic(Client client) {
		Card previousCard = null;
		int count = 0;
		StringBuilder result = new StringBuilder();
		ArrayList<Card> cards = User.getLoggedInUser().getDeck().getCards();
		cards.sort(Card::compareTo);
		for (Card card : cards) {
			if (previousCard == null) {
				previousCard = card;
				count++;
			} else if (previousCard.toString().equals(card.toString())) {
				count++;
			} else {
				result.append(previousCard.getName()).append(":").append(count).append("\n");
				previousCard = card;
				count = 1;
			}
		}
		if (previousCard != null) {
			result.append(previousCard.getName()).append(":").append(count).append("\n");
		}
		return new Result(result.toString(), true);
	}

	public static Result showInfo(Client client) {
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


	public static Result saveDeckByAddress(Client client, String address) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(User.getLoggedInUser().getDeck());
			oos.close();
		} catch (IOException e) {
			return new Result("Invalid address", false);
		}
		return new Result("Deck Saved Successfully", true);
	}

	public static Result saveDeckByName(Client client, String name) {
		String path = PreMatchMenusController.class.getResource("/decks").getPath();
		path = path + "/" + name + ".json";
		return saveDeckByAddress(path);
	}

	public static Result loadDeckByAddress(Client client, String address) {
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

	public static Result loadDeckByName(Client client, String name) {
		String path = PreMatchMenusController.class.getResource("/decks").getPath();
		path = path + "/" + name + ".json";
		return loadDeckByAddress(path);
	}

	public static Result showLeaders(Client client) {
		StringBuilder message = new StringBuilder();
		for (Leader leader : User.getLoggedInUser().getDeck().getAvailableLeaders())
			message.append(leader + "\n");
		return new Result(message.toString(), true);
	}

	public static Result showNowLeaderToGraphics(Client client) {
		return new Result(User.getLoggedInUser().getDeck().getLeader().getName(), true);
	}

	public static Result selectLeader(Client client, int leaderNumber) {
		if (leaderNumber < 0 || leaderNumber >= User.getLoggedInUser().getDeck().getAvailableLeaders().size())
			return new Result("Invalid Leader Number", false);
		User.getLoggedInUser().getDeck().setLeader(User.getLoggedInUser().getDeck().getAvailableLeaders().get(leaderNumber));
		return new Result("Leader Selected Successfully", true);
	}

	public static Result addToDeck(Client client, String cardName, int count) {
		if (count < 1) return new Result("Invalid Count", false);
		Card card = CardCreator.getCard(cardName);
		if (User.getLoggedInUser().getDeck().getAvailableCount(card) < count)
			return new Result("Not Enough Cards Available", false);
		for (int i = 0; i < count; i++)
			User.getLoggedInUser().getDeck().add(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Added Successfully", true);
	}

	public static Result deleteFromDeck(Client client, int cardNumber, int count) {
		if (cardNumber < 0 || cardNumber >= User.getLoggedInUser().getDeck().getCards().size())
			return new Result("Invalid Card Number", false);
		Card card = User.getLoggedInUser().getDeck().getCards().get(cardNumber);
		if (count < 1) return new Result("Invalid Count", false);
		if (User.getLoggedInUser().getDeck().getInDeckCount(card) < count)
			return new Result("Not Enough Cards in Deck", false);
		for (int i = 0; i < count; i++)
			User.getLoggedInUser().getDeck().remove(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Removed Successfully", true);
	}

	public static Result changeTurn(Client client) {
		if (!User.getLoggedInUser().getDeck().isValid()) return new Result("Your Deck Is Invalid", false);
		User temp = User.getLoggedInUser();
		User.setLoggedInUser(opponent);
		opponent = temp;
		cnt++;
		if (cnt % 2 == 0) {
			return startGame();
		} else {
			return new Result("Turn Changed Successfully", true);
		}
	}

	public static Result startGame(Client client) {
		Game.createGame(User.getLoggedInUser(), opponent);
		return new Result("Game Started Successfully", true);
	}

	public static Result exit(Client client) {
		Appview.setMenu(new MainMenu());
		return new Result("Exiting PreMatch Menu", true);
	}

	public static ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<>();
		for (User user : User.getUsers()) {
			usernames.add(user.getUsername());
		}
		return usernames;
	}
}
