package controller.game;

import model.Result;
import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Space;

import java.util.ArrayList;

public class MatchMenuController {

	public static Result vetoCard(int cardNumber) {
		// TODO:
		return null;
	}

	public static Result showHand(int number) {
		// TODO:
		return null;
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

	public static Unit askSpace(Space space) {
		return askSpace(space, false);
	}

	public static Unit askSpace(Space space, boolean isRandom) {
		ArrayList<Unit> nonHeroInSpace = new ArrayList<>();
		for (Card card : space.getCards()) {
			if (card instanceof Unit && !((Unit) card).isHero()) {
				nonHeroInSpace.add((Unit) card);
			}
		}
		if (nonHeroInSpace.isEmpty()) return null;
		if (isRandom) {
			int randomIndex = (int) (Math.random() * nonHeroInSpace.size());
			return nonHeroInSpace.get(randomIndex);
		}
		return null;
	}

	public static void showSpace(Space tmp) {
	}
}
