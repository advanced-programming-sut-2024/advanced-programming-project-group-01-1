package server.controller.game;

import message.Result;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.space.Space;

import java.util.ArrayList;
import java.util.Scanner;

public class MatchMenuController {

	public static Result vetoCard(int cardNumber) {
		// TODO:
		return null;
	}

	public static Result showHand(int number) {
		StringBuilder hand = new StringBuilder();
		if (number >= 0) hand.append(Game.getCurrentGame().getCurrentHand().getCards().get(number).toString());
		else hand.append(Game.getCurrentGame().getCurrentHand().toString());
		return new Result(hand.toString(), true);
	}

	public static Result remainingInDeck() {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentDeck().getCards().size()), true);
	}

	public static Result showDiscordPiles() {
		return new Result("Current Discard Pile:\n" + Game.getCurrentGame().getCurrentDiscardPile() +
				"Opponent Discard Pile:\n" + Game.getCurrentGame().getOpponentDiscardPile(), true);
	}

	public static Result showRow(int rowNumber) {
		return new Result(Game.getCurrentGame().getRow(rowNumber).toString(), true);
	}

	public static Result showWeatherSystem() {
		return new Result(Game.getCurrentGame().getCurrentWeatherSystem() + "\n" +
				Game.getCurrentGame().getOpponentWeatherSystem(), true);
	}

	public static Result placeCard(int cardNumber, int rowNumber) {
		try {
			Game.getCurrentGame().placeCard(Game.getCurrentGame().getCurrentHand().getCards().get(cardNumber), rowNumber);
			return new Result("Card placed successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showLeader() {
		return new Result(Game.getCurrentGame().getCurrentLeader().toString(), true);
	}

	public static Result useLeaderAbility() {
		try {
			Game.getCurrentGame().useLeaderAbility();
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showPlayersInfo() {
		return new Result("Current: " + Game.getCurrentGame().getCurrent().getUsername() + " " +
				Game.getCurrentGame().getCurrentFaction() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponent().getUsername() + " " +
				Game.getCurrentGame().getOpponentFaction(), true);
	}

	public static Result showPlayersLives() {
		return new Result("Current: " + Game.getCurrentGame().getCurrentLife() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponentLife(), true);
	}

	public static Result showHandSize() {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentHand().getCards().size()), true);
	}

	public static Result showTurnInfo() {
		return new Result(Game.getCurrentGame().getCurrent().getUsername(), true);
	}

	public static Result showTotalPower() {
		return new Result("Current: " + Game.getCurrentGame().getCurrentPower() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponentPower(), true);
	}

	public static Result showRowPower(int rowNumber) {
		return new Result(String.valueOf(Game.getCurrentGame().getRow(rowNumber).getSumOfPowers()), true);
	}

	public static Result passTurn() {
		Game.getCurrentGame().passTurn();
		return new Result("Turn passed successfully", true);
	}

	public static Card askSpace(Space space, boolean onlyUnit) {
		return askSpace(space, false, onlyUnit);
	}

	public static Card askSpace(Space space, boolean isRandom, boolean onlyUnit) {
		ArrayList<Card> availableCards = new ArrayList<>();
		for (Card card : space.getCards()) {
			if (card instanceof Unit) {
				if (!((Unit) card).isHero()) availableCards.add(card);
			} else if (!onlyUnit) availableCards.add(card);
		}
		System.out.println("Asking Space " + availableCards.size());
		if (availableCards.isEmpty()) return null;
		if (isRandom) {
			int randomIndex = (int) (Math.random() * availableCards.size());
			return availableCards.get(randomIndex);
		}
		// TODO: make it work with graphics and terminal
		Scanner scanner = new Scanner(System.in);
		int index = scanner.nextInt();
		if (index < 0 || index >= availableCards.size()) return null;
		return availableCards.get(index);
	}

	public static void showSpace(Space tmp) {
	}
}
