package controller.game;

import javafx.scene.layout.Pane;
import model.Result;
import model.card.Card;
import model.card.special.spell.Buffer;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Space;
import model.user.User;
import view.Appview;
import view.MainMenu;
import view.game.SelectPanel;
import view.game.SelectionHandler;
import view.game.prematch.MatchFinderMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class MatchMenuController {

	private static boolean isAsking = false;
	private static int cardsCount;
	private static SelectPanel selectPanel;

	public static boolean isAsking() {
		return isAsking;
	}

	public static boolean isRowDebuffed(int rowNumber) {
		return Game.getCurrentGame().getRow(rowNumber).isDebuffed();
	}

	public static Result vetoCard(int cardNumber) {
		// TODO:
		return null;
	}

	public static Result getUsernames() {
		return new Result(Game.getCurrentGame().getCurrentUsername() + "\n" +
				Game.getCurrentGame().getOpponentUsername() + "\n", true);
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
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentDeck().getCards().size()) + "\n" +
				Game.getCurrentGame().getOpponentDeck().getCards().size(), true);
	}

	public static Result showDiscordPiles() {
		return new Result("Current Discard Pile:\n" + Game.getCurrentGame().getCurrentDiscardPile() +
				"Opponent Discard Pile:\n" + Game.getCurrentGame().getOpponentDiscardPile(), true);
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
		return new Result(Game.getCurrentGame().getCurrentWeatherSystem() + "\n" +
				Game.getCurrentGame().getOpponentWeatherSystem(), true);
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

	public static Result useLeaderAbility() {
		try {
			Game.getCurrentGame().useLeaderAbility();
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showFactionsForGraphic() {
		return new Result(Game.getCurrentGame().getCurrentFaction() + "\n" +
				Game.getCurrentGame().getOpponentFaction(), true);
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
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentHand().getCards().size() +
				" - " + Game.getCurrentGame().getOpponentNumberOfCardsInHand()), true);
	}

	public static Result showCurrentHand() {
		return new Result(Game.getCurrentGame().getCurrentHand().toString(), true);
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

	public static void askCards(ArrayList<Card> cards, boolean isRandom, SelectionHandler selectionHandler) {
		if (cards.isEmpty()) return;
		if (isRandom) {
			int randomIndex = (int) (Math.random() * cards.size());
			selectionHandler.handle(randomIndex);
		} else {
			isAsking = true;
			cardsCount = cards.size();
			StringBuilder cardNames = new StringBuilder();
			for (Card card : cards)
				cardNames.append(card.getName()).append("\n").append("KTKM").append("\n");
			selectPanel = new SelectPanel((Pane) Appview.getStage().getScene().getRoot(),
					cardNames.toString().split("\n"), 0, selectionHandler, false);
		}
	}

	public static Result selectCard(int index) {
		if (!isAsking) return new Result("not asking", false);
		if (index < 0 || index >= cardsCount) return new Result("out of bound", false);
		selectPanel.selectCard(index);
		isAsking = false;
		return new Result("selected successfully", true);
	}

	public static void showSpace(Space tmp) {
	}

	public static void endGame() {
		Appview.setMenu(new MatchFinderMenu());
	}
}
