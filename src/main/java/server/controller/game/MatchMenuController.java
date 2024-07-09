package server.controller.game;

import server.main.CardCreator;
import server.model.Asker;
import message.Result;
import server.model.Client;
import server.model.card.Card;
import server.model.card.special.spell.Buffer;
import server.model.game.CardMover;
import server.model.game.Game;
import server.model.game.space.Row;
import server.model.game.space.Space;
import server.model.leader.Leader;
import server.model.user.Deck;
import server.model.user.User;
import server.view.game.prematch.MatchFinderMenu;

import java.util.ArrayList;

public class MatchMenuController {

	private static boolean isCurrent(Client client) {
		return client.getIdentity() == client.getIdentity().getCurrentGame().getCurrent();
	}

	private static Leader getLeader(Client client) {
		if (isCurrent(client)) return client.getIdentity().getCurrentGame().getCurrentLeader();
		return client.getIdentity().getCurrentGame().getOpponentLeader();
	}

	private static Space getHand(Client client) {
		if (isCurrent(client)) return client.getIdentity().getCurrentGame().getCurrentHand();
		return client.getIdentity().getCurrentGame().getOpponentHand();
	}

	private static Space getDeck(Client client) {
		if (isCurrent(client)) return client.getIdentity().getCurrentGame().getCurrentDeck();
		return client.getIdentity().getCurrentGame().getOpponentDeck();
	}

	private static Space getWeatherSystem(Client client) {
		if (isCurrent(client)) return client.getIdentity().getCurrentGame().getCurrentWeatherSystem();
		return client.getIdentity().getCurrentGame().getOpponentWeatherSystem();
	}

	private static Space getDiscardPile(Client client) {
		if (isCurrent(client)) return client.getIdentity().getCurrentGame().getCurrentDiscardPile();
		return client.getIdentity().getCurrentGame().getOpponentDiscardPile();
	}


	public static boolean isRowDebuffed(int rowNumber) {
		return Game.getCurrentGame().getRow(rowNumber).isDebuffed();
	}

	private static void vetoCard(Card card) {
		new Asker(Game.getCurrentGame().getCurrentDeck(), false, false, true, index -> {
			Card card1 = Game.getCurrentGame().getCurrentDeck().getCards().get(index);
			card1.updateSpace(Game.getCurrentGame().getCurrentHand());
		}, false, 0, true);
		card.updateSpace(Game.getCurrentGame().getCurrentDeck());
	}

	public static void handleVeto() {
		if (!Game.getCurrentGame().getCurrentLeader().isManual()) Game.getCurrentGame().getCurrentLeader().act();
		final Space hand = Game.getCurrentGame().getCurrentHand();
		new Asker(hand, false, false, false, index -> {
			if (index != -1) {
				vetoCard(hand.getCards().get(index));
				new Asker(hand, false, false, false, index1 -> {
					if (index1 != -1) vetoCard(hand.getCards().get(index1));
					Game.getCurrentGame().changeTurn();
					if (!Game.getCurrentGame().getCurrentLeader().isManual())
						Game.getCurrentGame().getCurrentLeader().act();
				}, true, index, true);
			} else {
				Game.getCurrentGame().changeTurn();
				if (!Game.getCurrentGame().getCurrentLeader().isManual())
					Game.getCurrentGame().getCurrentLeader().act();
			}
		}, true, 0);
		final Space hand1 = Game.getCurrentGame().getSpaceById(Game.OPPONENT_HAND);
		new Asker(hand1, false, false, false, index -> {
			if (index != -1) {
				vetoCard(hand1.getCards().get(index));
				new Asker(hand1, false, false, false, index1 -> {
					if (index1 != -1) vetoCard(hand1.getCards().get(index1));
					Game.getCurrentGame().changeTurn();
				}, true, index, true);
			} else Game.getCurrentGame().changeTurn();
		}, true, 0);
	}

	public static Result getUsernames(Client client) {
		User me = client.getIdentity();
		User opponent = client.getIdentity().getCurrentGame().getOpponent();
		if (opponent == me) opponent = client.getIdentity().getCurrentGame().getCurrent();
		return new Result(me.getUsername() + "\n" + opponent.getUsername() + "\n", true);
	}

	public static Result showHand(Client client, int number) {
		StringBuilder hand = new StringBuilder();
		if (number >= 0) hand.append(getHand(client).getCards().get(number).toString());
		else hand.append(getHand(client).toString());
		return new Result(hand.toString(), true);
	}

	public static Result showHandForGraphic(Client client) {
		StringBuilder hand = new StringBuilder();
		for (Card card : getHand(client).getCards()) {
			hand.append(card.toString()).append("\n");
			hand.append("unique code: ").append(card.toSuperString());
			hand.append("\n------------------\n");
		}
		return new Result(hand.toString(), true);
	}

	public static Result remainingInDecksForGraphic(Client client) {
		Space myDeck = getDeck(client);
		Space opponentDeck = client.getIdentity().getCurrentGame().getOpponentDeck();
		if (opponentDeck == myDeck) opponentDeck = client.getIdentity().getCurrentGame().getCurrentDeck();
		return new Result(myDeck.getCards().size() + "\n" + opponentDeck.getCards().size(), true);
	}

	public static Result showDiscardPilesForGraphic(Client client) {
		StringBuilder discardPiles = new StringBuilder();
		Space myPile = getDiscardPile(client);
		Space opponentPile = client.getIdentity().getCurrentGame().getOpponentDiscardPile();
		if (opponentPile == myPile) opponentPile = client.getIdentity().getCurrentGame().getOpponentDiscardPile();
		discardPiles.append("Current Discard Pile:\n");
		for (Card card : myPile.getCards()) {
			discardPiles.append(card.toString()).append("\n");
			discardPiles.append("unique code: ").append(card.toSuperString());
			discardPiles.append("\n------------------\n");
		}
		discardPiles.append("\n------------------\n");
		discardPiles.append("Opponent Discard Pile:\n");
		for (Card card : opponentPile.getCards()) {
			discardPiles.append(card.toString()).append("\n");
			discardPiles.append("unique code: ").append(card.toSuperString());
			discardPiles.append("\n------------------\n");
		}
		return new Result(discardPiles.toString(), true);
	}


	public static Result showRowForGraphic(Client client, int rowNumber) {
		StringBuilder row = new StringBuilder();
		if (!isCurrent(client)) rowNumber = 5 - rowNumber;
		Buffer buffer = client.getIdentity().getCurrentGame().getRow(rowNumber).getBuffer();
		if (buffer != null) {
			row.append("Buffer: ").append(buffer.toString()).append("\n");
			row.append("unique code: ").append(buffer.toSuperString());
			row.append("\n------------------\n");
		}
		for (Card card : client.getIdentity().getCurrentGame().getRow(rowNumber).getCards()) {
			row.append(card.toString()).append("\n");
			row.append("unique code: ").append(card.toSuperString());
			row.append("\n------------------\n");
		}
		return new Result(row.toString(), true);
	}


	public static Result showWeatherSystemForGraphic(Client client) {
		StringBuilder weather = new StringBuilder();
		Space myWeather = getWeatherSystem(client);
		Space opponentWeather = client.getIdentity().getCurrentGame().getOpponentWeatherSystem();
		if (myWeather == opponentWeather)
			opponentWeather = client.getIdentity().getCurrentGame().getOpponentWeatherSystem();
		for (Card card : myWeather.getCards()) {
			weather.append(card.toString()).append("\n");
			weather.append("unique code: ").append(card.toSuperString());
			weather.append("\n------------------\n");
		}
		for (Card card : opponentWeather.getCards()) {
			weather.append(card.toString()).append("\n");
			weather.append("unique code: ").append(card.toSuperString());
			weather.append("\n------------------\n");
		}
		return new Result(weather.toString(), true);
	}

	public static Result placeCard(Client client, int cardNumber, int rowNumber) {
		try {
			client.getIdentity().getCurrentGame().placeCard(getHand(client).getCards().get(cardNumber), rowNumber);
			return new Result("Card placed successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showLeadersForGraphic(Client client) {
		Leader myLeader = getLeader(client);
		Leader opponentLeader = client.getIdentity().getCurrentGame().getOpponentLeader();
		if (myLeader == opponentLeader) opponentLeader = client.getIdentity().getCurrentGame().getCurrentLeader();
		return new Result(myLeader + "\n" + "unique code: " + myLeader.toSuperString() + "\n------------------\n" +
				opponentLeader.toString() + "\n" + "unique code: " + opponentLeader.toSuperString(), true);
	}


	public static Result isLeadersDisable(Client client) {
		Leader myLeader = getLeader(client);
		Leader opponentLeader = client.getIdentity().getCurrentGame().getOpponentLeader();
		if (opponentLeader == myLeader) opponentLeader = client.getIdentity().getCurrentGame().getCurrentLeader();
		return new Result(myLeader.isDisable() + "\n" + .isDisable(), true);
	}

	public static Result useLeaderAbility(Client client) {
		try {
			Game.getCurrentGame().useLeaderAbility();
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result passedState(Client client) {
		return new Result(Game.getCurrentGame().hasPassed() + "\n" + Game.getCurrentGame().hasOpponentPassed(), true);
	}

	public static Result showFactionsForGraphic(Client client) {
		return new Result(Game.getCurrentGame().getCurrentFaction() + "\n" + Game.getCurrentGame().getOpponentFaction(), true);
	}


	public static Result showPlayersLives(Client client) {
		return new Result("Current: " + Game.getCurrentGame().getCurrentLife() + "\n" + "Opponent: " + Game.getCurrentGame().getOpponentLife(), true);
	}

	public static Result showHandSize(Client client) {
		return new Result(String.valueOf(Game.getCurrentGame().getCurrentHand().getCards().size() + " - " + Game.getCurrentGame().getOpponentNumberOfCardsInHand()), true);
	}

	public static Result passTurn(Client client) {
		Game.getCurrentGame().passTurn();
		return new Result("Turn passed successfully", true);
	}

	public static Result isAsking(Client client) {
		if (Asker.isAsking()) return new Result("asking", true);
		return new Result("not asking", false);
	}

	public static Result getAskerCards(Client client) {
		if (!Asker.isAsking()) return new Result("not asking", false);
		StringBuilder cards = new StringBuilder();
		for (Card card : Asker.getRunning().getCards()) {
			cards.append(card.getName()).append("\n").append(card.getDescription()).append("\n");
		}
		return new Result(cards.toString(), true);
	}

	public static Result getAskerPtr(Client client) {
		if (!Asker.isAsking()) return new Result("not asking", false);
		return new Result(String.valueOf(Asker.getRunning().getPtr()), true);
	}

	public static Result isAskerOptional(Client client) {
		if (!Asker.isAsking()) return new Result("not asking", false);
		if (Asker.getRunning().isOptional()) return new Result("optional", true);
		return new Result("not optional", false);
	}

	public static Result selectCard(Client client, int index) {
		if (Asker.select(index)) return new Result("success", true);
		return new Result("failure", false);
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

	public static Result getPowers(Client client) {
		String powers = Game.getCurrentGame().getCurrentPower() + "\n"
				+ Game.getCurrentGame().getOpponentPower() + "\n";
		return new Result(powers, true);
	}

	public static Result getScores(Client client) {
		ArrayList<Integer> scores = Game.getCurrentGame().getCurrentScores();
		ArrayList<Integer> opponentScores = Game.getCurrentGame().getOpponentScores();
		StringBuilder Result = new StringBuilder(Client client);
		for (int i = 0; i < scores.size(); i++) {
			result.append(scores.get(i)).append("\n").append(opponentScores.get(i)).append("\n");
		}
		return new Result(result.toString(), true);
	}

	public static Result cheatClearWeather(Client client) {
		Game.getCurrentGame().getCurrentWeatherSystem().clear(Game.getCurrentGame().getCurrentDiscardPile(), null);
		Game.getCurrentGame().getOpponentWeatherSystem().clear(Game.getCurrentGame().getOpponentDiscardPile(), null);
		return new Result("Weather cleared", true);
	}

	public static Result cheatClearRow(Client client, int rowNumber) {
		Game.getCurrentGame().getRow(rowNumber).clear(rowNumber < 3 ? Game.getCurrentGame().getCurrentDiscardPile() : Game.getCurrentGame().getOpponentDiscardPile(), null);
		return new Result("Row cleared", true);
	}

	public static Result cheatDebuffRow(Client client, int rowNumber) {
		Card card = CardCreator.getCard(rowNumber == 2 ? "Biting Frost" : rowNumber == 1 ? "Impenetrable Fog" : "Torrential Rain");
		card.setSpace(Game.getCurrentGame().getCurrentDeck());
		try {
			card.put(-1);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
		return new Result("Row debuffed", true);
	}

	public static Result cheatHeal(Client client) {
		Game.getCurrentGame().setCurrentLife(2);
		return new Result("Recovered Crystal", true);
	}

	public static Result cheatAddCard(Client client, String cardName) {
		Card card = CardCreator.getCard(cardName);
		if (card == null) return new Result("Card not found", false);
		card.setSpace(Game.getCurrentGame().getCurrentDeck());
		card.updateSpace(Game.getCurrentGame().getCurrentHand());
		return new Result("Card added to hand", true);
	}

	public static Result cheatMoveFromDeckToHand(Client client) {
		new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1, false, false).move();
		return new Result("Card moved from deck to hand", true);
	}

	public static Result cheatAddPower(Client client, int power) {
		Game.getCurrentGame().addCheatPower(power);
		return new Result("Power added", true);
	}
}
