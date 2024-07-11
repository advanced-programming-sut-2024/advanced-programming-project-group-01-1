package server.controller.game;

import message.Result;
import server.main.CardCreator;
import server.model.Client;
import server.model.card.Card;
import server.model.game.Faction;
import server.model.game.Game;
import server.model.leader.Leader;
import server.model.tournament.Bracket;
import server.model.user.Deck;
import server.model.user.User;
import server.view.MainMenu;
import server.view.game.MatchMenu;
import server.view.game.TournamentMenu;
import server.view.game.prematch.LobbyMenu;
import server.view.game.prematch.MatchFinderMenu;
import server.view.game.prematch.QuickMatchMenu;

import java.util.ArrayList;

public class PreMatchMenusController {
	private static final ArrayList<User> tournamentQueue = new ArrayList<>();
	private static final ArrayList<User> quickMatches = new ArrayList<>();
	public static Result requestMatch(Client client, String opponentUsername) {
		User opponent = User.getUserByUsername(opponentUsername);
		if (opponent == null) return new Result("User not found", false);
		if (opponent.equals(client.getIdentity())) return new Result("You cannot play with yourself", false);
		if (!User.getOnlineUsers().contains(opponent)) return new Result("User is not online", false);
		if (Client.getClient(opponent).isInGame()) return new Result("User is in another game", false);
		if (client.isWaiting()) return new Result("You cannot send two requests simultaneously", false);
		if (opponent.getChallengedUser() == client.getIdentity()) {
			if (handleMatchRequest(client, opponentUsername, true).isSuccessful())
				return new Result("Go to Lobby", true);
		}
		client.getIdentity().setChallengedUser(opponent);
		opponent.getMatchRequests().add(client.getIdentity());
		client.setWaiting(true);
		return new Result("Request sent", true);
	}

	public static Result enterTournament(Client client) {
		if (client.getIdentity().getCurrentBracket() != null) {
			client.setMenu(new TournamentMenu());
			return new Result("Go to Tournament", true);
		}
		synchronized (tournamentQueue) {
			tournamentQueue.add(client.getIdentity());
			if (tournamentQueue.size() == 8) {
				User[] players = tournamentQueue.toArray(new User[0]);
				Bracket bracket = new Bracket(players);
				for (int i = 0; i < 8; i++) players[i].setCurrentBracket(bracket);
				tournamentQueue.clear();
				client.setMenu(new TournamentMenu());
				bracket.start();
				return new Result("Go to Tournament", true);
			}
			client.setWaiting(true);
			return new Result("Added to queue", true);
		}
	}

	public static Result checkTournament(Client client) {
		if (!client.isWaiting()) return new Result("You were not waiting", false);
		if (client.getIdentity().getCurrentBracket() == null) return new Result("Still wait", false);
		client.setWaiting(false);
		client.setMenu(new TournamentMenu());
		return new Result("Tournament starting", true);
	}

	public static Result getMatchRequests(Client client) {
		StringBuilder requests = new StringBuilder();
		for (User requestSender : client.getIdentity().getMatchRequests()) {
			requests.append(requestSender.getUsername()).append("\n");
		}
		return new Result(requests.toString(), true);
	}

	public static Result handleMatchRequest(Client client, String senderUsername, boolean accept) {
		User sender = User.getUserByUsername(senderUsername);
		if (sender == null) return new Result("no such user", false);
		if (!client.getIdentity().getMatchRequests().contains(sender))
			return new Result("No request from this user", false);
		client.getIdentity().getMatchRequests().remove(sender);
		if (accept) {
			client.getIdentity().setChallengedUser(sender);
			client.setInGame(true);
			client.setMenu(new LobbyMenu());
			return new Result("Request accepted", true);
		} else return new Result("Request rejected", true);
	}


	public static Result checkRequest(Client client) {
		if (!client.isWaiting()) return new Result("You were not waiting", false);
		if (client.getIdentity() == client.getIdentity().getChallengedUser().getChallengedUser()) {
			client.setInGame(true);
			client.setWaiting(false);
			client.setMenu(new LobbyMenu());
			return new Result("You are accepted", true);
		}
		if (!client.getIdentity().getChallengedUser().getMatchRequests().contains(client.getIdentity()))
			return new Result("You are rejected", true);
		return new Result("Still wait", false);
	}

	public static Result stopWait(Client client) {
		if (!client.isWaiting()) return new Result("You were not waiting", false);
		synchronized (tournamentQueue) {
			tournamentQueue.remove(client.getIdentity());
		}
		client.getIdentity().getChallengedUser().getMatchRequests().remove(client.getIdentity());
		client.getIdentity().setChallengedUser(null);
		client.setWaiting(false);
		return new Result("stopped successfully", true);
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
		client.getIdentity().setDeck(new Deck(faction));
		return new Result("Faction Selected Successfully", true);
	}

	public static Result showNowFactionToGraphics(Client client) {
		return new Result(client.getIdentity().getDeck().getFaction().getName(), true);
	}

	public static Result showCards(Client client) {
		StringBuilder message = new StringBuilder();
		for (Card card : client.getIdentity().getDeck().getAvailableCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showCardsForGraphic(Client client) {
		ArrayList<Card> cards = client.getIdentity().getDeck().getAvailableCards();
		return new Result(showCardSForGraphic(cards), true);
	}

	public static Result showDeck(Client client) {
		StringBuilder message = new StringBuilder();
		for (Card card : client.getIdentity().getDeck().getCards()) {
			message.append(card.toString()).append("\n------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showDeckForGraphic(Client client) {
		ArrayList<Card> cards = client.getIdentity().getDeck().getCards();
		return new Result(showCardSForGraphic(cards), true);
	}

	private static String showCardSForGraphic(ArrayList<Card> cards) {
		Card previousCard = null;
		int count = 0;
		StringBuilder result = new StringBuilder();
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
		return result.toString();
	}

	public static Result showInfo(Client client) {
		StringBuilder message = new StringBuilder();
		message.append("Username: ").append(client.getIdentity().getUsername()).append("\n");
		message.append("Faction: ").append(client.getIdentity().getDeck().getFaction()).append("\n");
		message.append("Leader: ").append(client.getIdentity().getDeck().getLeader()).append("\n");
		message.append("Number of Cards in Deck: ").append(client.getIdentity().getDeck().getCards().size()).append("\n");
		message.append("Number of Special Cards in Deck: ").append(client.getIdentity().getDeck().getSpecialCount()).append("\n");
		message.append("Number of Unit Cards in Deck: ").append(client.getIdentity().getDeck().getUnitCount()).append("\n");
		message.append("Number of Hero Cards in Deck: ").append(client.getIdentity().getDeck().getHeroCount()).append("\n");
		message.append("Total Power of Deck: ").append(client.getIdentity().getDeck().getTotalPower()).append("\n");
		return new Result(message.toString(), true);
	}


	public static Result saveDeck(Client client) {
		String fson = client.getIdentity().getDeck().fuckGson();
		return new Result(fson, true);
	}

	public static Result loadDeck(Client client, String fson) {
		Deck deck = Deck.fsonReader(fson);
		client.getIdentity().setDeck(deck);
		return new Result("Loaded deck successfully", true);
	}

	public static Result showLeaders(Client client) {
		StringBuilder message = new StringBuilder();
		for (Leader leader : client.getIdentity().getDeck().getAvailableLeaders())
			message.append(leader + "\n");
		return new Result(message.toString(), true);
	}

	public static Result showNowLeaderToGraphics(Client client) {
		return new Result(client.getIdentity().getDeck().getLeader().getName(), true);
	}

	public static Result selectLeader(Client client, int leaderNumber) {
		if (leaderNumber < 0 || leaderNumber >= client.getIdentity().getDeck().getAvailableLeaders().size())
			return new Result("Invalid Leader Number", false);
		client.getIdentity().getDeck().setLeader(client.getIdentity().getDeck().getAvailableLeaders().get(leaderNumber));
		return new Result("Leader Selected Successfully", true);
	}

	public static Result addToDeck(Client client, String cardName, int count) {
		if (count < 1) return new Result("Invalid Count", false);
		Card card = CardCreator.getCard(cardName);
		if (client.getIdentity().getDeck().getAvailableCount(card) < count)
			return new Result("Not Enough Cards Available", false);
		for (int i = 0; i < count; i++)
			client.getIdentity().getDeck().add(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Added Successfully", true);
	}

	public static Result deleteFromDeck(Client client, int cardNumber, int count) {
		if (cardNumber < 0 || cardNumber >= client.getIdentity().getDeck().getCards().size())
			return new Result("Invalid Card Number", false);
		Card card = client.getIdentity().getDeck().getCards().get(cardNumber);
		if (count < 1) return new Result("Invalid Count", false);
		if (client.getIdentity().getDeck().getInDeckCount(card) < count)
			return new Result("Not Enough Cards in Deck", false);
		for (int i = 0; i < count; i++)
			client.getIdentity().getDeck().remove(card);
		return new Result(count > 1 ? "Cards" : "Card" + " Removed Successfully", true);
	}

	public static Result exit(Client client) {
		client.setMenu(new MainMenu());
		return new Result("Exiting PreMatch Menu", true);
	}

	public static Result getOtherUsernames(Client client) {
		StringBuilder usernames = new StringBuilder();
		for (User user : User.getUsers()) {
			if (user != client.getIdentity()) usernames.append(user.getUsername()).append("\n");
		}
		return new Result(usernames.toString(), true);
	}


	public static Result isDeckValid(Client client) {
		if (client.getIdentity().getDeck().isValid()) return new Result("Valid", true);
		return new Result("Invalid", false);
	}

	public static Result getReady(Client client) {
		if (!isDeckValid(client).isSuccessful()) return new Result("Deck is not ready", false);
		if (Client.getClient(client.getIdentity().getChallengedUser()).isWaiting()) {
			User me = client.getIdentity();
			User opponent = client.getIdentity().getChallengedUser();
			Game game = Game.createGame(me, opponent);
			me.setCurrentGame(game);
			opponent.setCurrentGame(game);
			MatchMenuController.handleVeto(game);
			client.setWaiting(true);
			client.setMenu(new MatchMenu());
			return new Result("game started", true);
		}
		client.setWaiting(true);
		return new Result("wait for your opponent", true);
	}

	public static Result cancelReady(Client client) {
		if (!client.isWaiting()) return new Result("You were not waiting", false);
		client.setWaiting(false);
		return new Result("canceled successfully", true);
	}

	public static Result checkOpponentReady(Client client) {
		if (!client.isWaiting()) return new Result("You are not ready", false);
		if (!Client.getClient(client.getIdentity().getChallengedUser()).isWaiting())
			return new Result("Opponent is not ready", false);
		User me = client.getIdentity();
		User opponent = client.getIdentity().getChallengedUser();
		client.setWaiting(false);
		Client.getClient(opponent).setWaiting(false);
		me.setChallengedUser(null);
		opponent.setChallengedUser(null);
		client.setMenu(new MatchMenu());
		return new Result("Opponent is ready", true);
	}

	public static Result setPreferFirst(Client client, boolean preferFirst) {
		client.getIdentity().getDeck().setPreferFirst(preferFirst);
		System.out.println(client.getIdentity().getDeck().doesPreferFirst());
		return new Result("Preferred First Set Successfully", true);
	}

	public static Result getPreference(Client client) {
		return new Result(client.getIdentity().getDeck().doesPreferFirst() ? "First" : "Second", true);
	}

	public static Result goToQuickMatchMenu(Client client) {
		client.setMenu(new QuickMatchMenu());
		return new Result("go to quick match menu", true);
	}

	public static Result getQuickMatchList() {
		synchronized (quickMatches) {
			StringBuilder matches = new StringBuilder();
			for (User user : quickMatches) {
				matches.append(user.getUsername()).append("\n");
			}
			return new Result(matches.toString(), true);
		}
	}

	public static Result startQuickMatch(Client client, String opponentUsername) {
		synchronized (quickMatches) {
			User user = User.getUserByUsername(opponentUsername);
			if (user == null) return new Result("no such user exists", false);
			synchronized (user) {
				if (!quickMatches.contains(user)) return new Result("Match either started or canceled", false);
				quickMatches.remove(user);
				client.getIdentity().setChallengedUser(user);
				user.setChallengedUser(client.getIdentity());
				client.setInGame(true);
				client.setMenu(new LobbyMenu());
				return new Result("Go to Lobby Menu", true);
			}
		}
	}

	public static Result createNewQuickMatch(Client client) {
		synchronized (quickMatches) {
			if (quickMatches.contains(client.getIdentity())) return new Result("already created", false);
			quickMatches.add(client.getIdentity());
			client.setWaiting(true);
			return new Result("match created", true);
		}
	}

	public static Result checkMatchReady(Client client) {
		synchronized (client.getIdentity()) {
			if (client.getIdentity().getChallengedUser() != null) {
				client.setInGame(true);
				client.setWaiting(false);
				client.setMenu(new LobbyMenu());
				return new Result("Go to Lobby Menu", true);
			}
			return new Result("not ready", false);
		}
	}

	public static Result cancelQuickMatch(Client client) {
		synchronized (quickMatches) {
			synchronized (client.getIdentity()) {
				System.out.println("wtf :: " + client.getIdentity().getChallengedUser());
				if (client.getIdentity().getChallengedUser() != null)
					return new Result("match already started", false);
				quickMatches.remove(client.getIdentity());
				client.setWaiting(false);
				return new Result("match canceled", true);
			}
		}
	}

	public static Result backToMatchFinder(Client client) {
		client.setMenu(new MatchFinderMenu());
		return new Result("back to match finder", true);
	}
}
