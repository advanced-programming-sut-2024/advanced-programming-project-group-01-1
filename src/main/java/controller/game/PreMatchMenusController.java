package controller.game;

import model.Result;
import model.card.Card;
import model.card.special.Decoy;
import model.card.special.spell.Buffer;
import model.card.special.spell.Weather;
import model.card.unit.Unit;
import model.game.Faction;
import model.game.Game;
import model.leader.Leader;
import model.user.Deck;
import model.user.User;

import java.util.ArrayList;

public class PreMatchMenusController {

	public static Result createGame(String opponentUsername) {
		User opponent = User.getUserByUsername(opponentUsername);
		if (opponent == null) return new Result("User Not Found", false);
		if (opponent.equals(User.getLoggedInUser())) return new Result("You Cannot Play With Yourself", false);
		if (!User.getLoggedInUser().getDeck().isValid()) return new Result("Your Deck Is Invalid", false);
		if (!opponent.getDeck().isValid()) return new Result("Opponent's Deck Is Invalid", false);
		Game.createGame(User.getLoggedInUser(), opponent);
		return new Result("Game Created Successfully", true);
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
			message.append(printCard(card)).append("------------------\n");
		}
		return new Result(message.toString(), true);
	}

	public static Result showDeck() {
		StringBuilder message = new StringBuilder();
		for (Card card : User.getLoggedInUser().getDeck().getCards()) {
			message.append(printCard(card)).append("------------------\n");
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

	public static Result saveDeckByAddress(String address) {
		// TODO:
		return null;
	}

	public static Result saveDeckByName(String name) {
		// TODO:
		return null;
	}

	public static Result loadDeckByAddress(String address) {
		// TODO:
		return null;
	}

	public static Result loadDeckByName(String name) {
		// TODO:
		return null;
	}

	public static Result showLeaders() {
		return null;
	}

	public static Result selectLeader(int leaderNumber) {
		// TODO:
		return null;
	}

	public static Result addToDeck(String cardName, int count) {
		// TODO:
		return null;
	}

	public static Result deleteFromDeck(int cardNumber, int count) {
		// TODO:
		return null;
	}

	public static Result changeTurn() {
		// TODO:
		return null;
	}

	public static Result startGame() {
		// TODO:
		return null;
	}


	private static String printCard(Card card) {
		StringBuilder result = new StringBuilder();
		result.append(card.getName()).append(" - ").append(card instanceof Unit ? "Unit" : "Special").append("\n");
		result.append("Placement: ").append(card instanceof Unit ? card.getClass().getSimpleName() :
				(card instanceof Decoy ? "Replaces any Card in Rows" :
					(card instanceof Weather ? "Weather System" :
							card instanceof Buffer ? "Row Buffer" : "Instant")));
		result.append(card.getAbility() == null ? "No Ability" : card.getAbility().toString()).append("\n");
		result.append("Available: ").append(User.getLoggedInUser().getDeck().getAvailableCount(card)).append("\n");
		if (card instanceof Unit) result.append("Power: ").append(((Unit) card).getPower()).append("\n");
		return result.toString();
	}

}
