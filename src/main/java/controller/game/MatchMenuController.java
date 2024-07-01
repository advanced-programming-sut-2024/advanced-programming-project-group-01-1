package controller.game;

import model.Result;
import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Space;

import java.util.ArrayList;

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
			Game.getCurrentGame().getCurrentHand().getCards().get(cardNumber).put(rowNumber);
			return new Result("Card placed successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showLeader() {
		return new Result(Game.getCurrentGame().getCurrentLeader().toString(), true);
	}

	public static Result useLeaderAbility() {
		// TODO:
		return null;
	}

	public static Result showPlayerInfo() {
		// TODO:
		return null;
	}

	public static Result showPlayerLives() {
		// TODO:
		return null;
	}

	public static Result showHandSize() {
		// TODO:
		return null;
	}

	public static Result showTurnInfo() {
		// TODO:
		return null;
	}

	public static Result showTotalPower() {
		// TODO:
		return null;
	}

	public static Result showRowPower(int rowNumber) {
		// TODO:
		return null;
	}

	public static Result passTurn() {
		// TODO:
		return null;
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
		if (availableCards.isEmpty()) return null;
		if (isRandom) {
			int randomIndex = (int) (Math.random() * availableCards.size());
			return availableCards.get(randomIndex);
		}
		return null;
	}

	public static void showSpace(Space tmp) {
	}
}
