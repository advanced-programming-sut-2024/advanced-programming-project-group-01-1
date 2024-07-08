package controller.game;

import javafx.application.Platform;
import model.Asker;
import model.Result;
import model.card.Card;
import model.card.special.spell.Buffer;
import model.game.CardMover;
import model.game.Game;
import model.game.space.Space;
import view.Appview;
import view.game.MatchMenu;
import view.game.SelectionHandler;
import view.game.prematch.MatchFinderMenu;

import java.util.ArrayList;

public class MatchMenuController {

	public static boolean isRowDebuffed(int rowNumber) {
		return Game.getCurrentGame().getRow(rowNumber).isDebuffed();
	}

	private static void vetoCard(Card card) {
		Game.getCurrentGame().getCurrentHand().getCards().remove(card);
		new Asker(Game.getCurrentGame().getCurrentDeck().getCards(), true, index -> {
			Card card1 = Game.getCurrentGame().getCurrentDeck().getCards().get(index);
			Game.getCurrentGame().getCurrentDeck().getCards().remove(card1);
			Game.getCurrentGame().getCurrentHand().getCards().add(card1);
		}, false, 0, true);
		Game.getCurrentGame().getCurrentDeck().getCards().add(card);
	}

	public static void handleVeto() {
		ArrayList<Card> hand = Game.getCurrentGame().getCurrentHand().getCards();
		askCards(hand, false, index -> {
			if (index != -1) {
				vetoCard(hand.get(index));
				new Asker(hand, false, index1 -> {
					if (index1 != -1) vetoCard(hand.get(index1));
					Game.getCurrentGame().changeTurn();
				}, true, index, true);
			} else Game.getCurrentGame().changeTurn();
		}, true, 0);
		ArrayList<Card> hand1 = Game.getCurrentGame().getSpaceById(Game.OPPONENT_HAND).getCards();
		askCards(hand1, false, index -> {
			if (index != -1) {
				vetoCard(hand1.get(index));
				new Asker(hand1, false, index1 -> {
					if (index1 != -1) vetoCard(hand1.get(index1));
					Game.getCurrentGame().changeTurn();
				}, true, index, true);
			} else Game.getCurrentGame().changeTurn();
		}, true, 0);
	}

	public static Result getUsernames() {
		return new Result(Game.getCurrentGame().getCurrentUsername() + "\n" + Game.getCurrentGame().getOpponentUsername() + "\n", true);
	}

	public static Result showHand(int number) {
		StringBuilder hand = new StringBuilder();
		if (number >= 0) hand.append(Game.getCurrentGame().getCurrentHand().getCards().get(number).toString());
		else hand.append(Game.getCurrentGame().getCurrentHand().toString());
		return new Result(hand.toString(), true);
	}

	public static Result showHandForGraphic() {
		StringBuilder hand = new StringBuilder();
		for (Card card : Game.getCurrentGame().getCurrentHand().getCards()) {
			hand.append(card.toString()).append("\n");
			hand.append("unique code: ").append(card.toSuperString());
			hand.append("\n------------------\n");
		}
		return new Result(hand.toString(), true);
	}

	public static Result remainingInDeck() {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentDeck().getCards().size()), true);
	}

	public static Result remainingInDecksForGraphic() {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentDeck().getCards().size()) + "\n" + Game.getCurrentGame().getOpponentDeck().getCards().size(), true);
	}

	public static Result showDiscordPiles() {
		return new Result("Current Discard Pile:\n" + Game.getCurrentGame().getCurrentDiscardPile() + "Opponent Discard Pile:\n" + Game.getCurrentGame().getOpponentDiscardPile(), true);
	}

	public static Result showDiscardPilesForGraphic() {
		StringBuilder discardPiles = new StringBuilder();
		discardPiles.append("Current Discard Pile:\n");
		for (Card card : Game.getCurrentGame().getCurrentDiscardPile().getCards()) {
			discardPiles.append(card.toString()).append("\n");
			discardPiles.append("unique code: ").append(card.toSuperString());
			discardPiles.append("\n------------------\n");
		}
		discardPiles.append("\n------------------\n");
		discardPiles.append("Opponent Discard Pile:\n");
		for (Card card : Game.getCurrentGame().getOpponentDiscardPile().getCards()) {
			discardPiles.append(card.toString()).append("\n");
			discardPiles.append("unique code: ").append(card.toSuperString());
			discardPiles.append("\n------------------\n");
		}
		return new Result(discardPiles.toString(), true);
	}

	public static Result showRow(int rowNumber) {
		return new Result(Game.getCurrentGame().getRow(rowNumber).toString(), true);
	}

	public static Result showRowForGraphic(int rowNumber) {
		StringBuilder row = new StringBuilder();
		Buffer buffer = Game.getCurrentGame().getRow(rowNumber).getBuffer();
		if (buffer != null) {
			row.append("Buffer: ").append(buffer.toString()).append("\n");
			row.append("unique code: ").append(buffer.toSuperString());
			row.append("\n------------------\n");
		}
		for (Card card : Game.getCurrentGame().getRow(rowNumber).getCards()) {
			row.append(card.toString()).append("\n");
			row.append("unique code: ").append(card.toSuperString());
			row.append("\n------------------\n");
		}
		return new Result(row.toString(), true);
	}

	public static Result showWeatherSystem() {
		return new Result(Game.getCurrentGame().getCurrentWeatherSystem() + "\n" + Game.getCurrentGame().getOpponentWeatherSystem(), true);
	}

	public static Result showWeatherSystemForGraphic() {
		StringBuilder weather = new StringBuilder();
		for (Card card : Game.getCurrentGame().getCurrentWeatherSystem().getCards()) {
			weather.append(card.toString()).append("\n");
			weather.append("unique code: ").append(card.toSuperString());
			weather.append("\n------------------\n");
		}
		for (Card card : Game.getCurrentGame().getOpponentWeatherSystem().getCards()) {
			weather.append(card.toString()).append("\n");
			weather.append("unique code: ").append(card.toSuperString());
			weather.append("\n------------------\n");
		}
		return new Result(weather.toString(), true);
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

	public static Result showLeadersForGraphic() {
		return new Result(Game.getCurrentGame().getCurrentLeader().toString() + "\n" + "unique code: " + Game.getCurrentGame().getCurrentLeader().toSuperString() + "\n------------------\n" + Game.getCurrentGame().getOpponentLeader().toString() + "\n" + "unique code: " + Game.getCurrentGame().getOpponentLeader().toSuperString(), true);
	}

	public static Result useLeaderAbility() {
		try {
			Game.getCurrentGame().useLeaderAbility();
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showFactionsForGraphic() {
		return new Result(Game.getCurrentGame().getCurrentFaction() + "\n" + Game.getCurrentGame().getOpponentFaction(), true);
	}

	public static Result showPlayersInfo() {
		return new Result("Current: " + Game.getCurrentGame().getCurrent().getUsername() + " " + Game.getCurrentGame().getCurrentFaction() + "\n" + "Opponent: " + Game.getCurrentGame().getOpponent().getUsername() + " " + Game.getCurrentGame().getOpponentFaction(), true);
	}

	public static Result showPlayersLives() {
		return new Result("Current: " + Game.getCurrentGame().getCurrentLife() + "\n" + "Opponent: " + Game.getCurrentGame().getOpponentLife(), true);
	}

	public static Result showHandSize() {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentHand().getCards().size() + " - " + Game.getCurrentGame().getOpponentNumberOfCardsInHand()), true);
	}

	public static Result showCurrentHand() {
		return new Result(Game.getCurrentGame().getCurrentHand().toString(), true);
	}

	public static Result showTurnInfo() {
		return new Result(Game.getCurrentGame().getCurrent().getUsername(), true);
	}

	public static Result showTotalPower() {
		return new Result("Current: " + Game.getCurrentGame().getCurrentPower() + "\n" + "Opponent: " + Game.getCurrentGame().getOpponentPower(), true);
	}

	public static Result showRowPower(int rowNumber) {
		return new Result(String.valueOf(Game.getCurrentGame().getRow(rowNumber).getSumOfPowers()), true);
	}

	public static Result passTurn() {
		Game.getCurrentGame().passTurn();
		return new Result("Turn passed successfully", true);
	}

	public static void askCards(ArrayList<Card> cards, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr) {
		new Asker(cards, isRandom, selectionHandler, isOptional, ptr);
	}

	public static Result selectCard(int index) {
		if (Asker.select(index)) return new Result("success", true);
		return new Result("failure", false);
	}

	public static void showSpace(Space tmp) {
	}

	public static void endGame() {
		Appview.setMenu(new MatchFinderMenu());
	}

	public static boolean isGameOver() {
		return Game.getCurrentGame().isGameOver();
	}

	public static boolean isGameWin() {
		return Game.getCurrentGame().isGameWin();
	}

	public static boolean isGameDraw() {
		return Game.getCurrentGame().isGameDraw();
	}

	public static Result getScores() {
		ArrayList<Integer> scores = Game.getCurrentGame().getCurrentScores();
		ArrayList<Integer> opponentScores = Game.getCurrentGame().getOpponentScores();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < scores.size(); i++) {
			result.append(scores.get(i)).append("\n").append(opponentScores.get(i)).append("\n");
		}
		return new Result(result.toString(), true);
	}
}
