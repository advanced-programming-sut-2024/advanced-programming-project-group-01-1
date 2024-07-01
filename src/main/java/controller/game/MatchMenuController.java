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
		else {
			for (Card card : Game.getCurrentGame().getCurrentHand().getCards()) {
				hand.append(card.toString()).append("\n");
			}
		}
		return new Result(hand.toString(), true);
	}

	public static Result remainingInDeck() {
		// TODO:
		return null;
	}

	public static Result showDiscordPile() {
		// TODO:
		return null;
	}

	public static Result showRow(int rowNumber) {
		// TODO:
		return null;
	}

	public static Result showWeatherSystem() {
		// TODO:
		return null;
	}

	public static Result placeCard(int cardNumber, int rowNumber) {
		// TODO:
		return null;
	}

	public static Result showLeader() {
		// TODO:
		return null;
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
