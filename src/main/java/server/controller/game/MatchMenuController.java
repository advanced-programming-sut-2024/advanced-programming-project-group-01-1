package server.controller.game;

import server.main.CardCreator;
import server.model.Asker;
import message.Result;
import server.model.Client;
import server.model.card.Card;
import server.model.card.special.spell.Buffer;
import server.model.game.CardMover;
import server.model.game.Faction;
import server.model.game.Game;
import server.model.game.Move;
import server.model.game.space.Row;
import server.model.game.space.Space;
import server.model.leader.Leader;
import server.model.user.Deck;
import server.model.user.User;
import server.view.game.prematch.MatchFinderMenu;

import java.util.ArrayList;
import java.util.Objects;

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

	public static Result isOpponentOnline(Client client) {
		User opponent = isCurrent(client) ? client.getIdentity().getCurrentGame().getOpponent() :
			client.getIdentity().getCurrentGame().getCurrent();
		if (User.getOnlineUsers().contains(opponent)) return new Result("Online", true);
		if (client.getIdentity().getCurrentGame().isGameOver())
			return new Result("Game Over", false);
		return new Result("Offline", false);
	}

	public static Result isRowDebuffed(Client client, int rowNumber) {
		if (!isCurrent(client)) rowNumber = 5 - rowNumber;
		if(client.getIdentity().getCurrentGame().getRow(rowNumber).isDebuffed())
			return new Result("debuffed", true);
		return new Result("not debuffed", false);
	}

	private static void vetoCard(Card card, User player, Space deck, Space hand) {
		new Asker(player, deck, false, false, true, index -> {
			Card card1 = deck.getCards().get(index);
			card1.updateSpace(hand);
		}, false, 0, true);
		card.updateSpace(deck);
	}

	private static void handleVeto(User player, Space hand, Space deck) {
		new Asker(player, hand, false, false, false, index -> {
			if (index != -1) {
				vetoCard(hand.getCards().get(index), player, deck, hand);
				new Asker(player, hand, false, false, false, index1 -> {
					if (index1 != -1) vetoCard(hand.getCards().get(index1), player, deck, hand);
				}, true, index, true);
			}
		}, true, 0);
	}

	public static void handleVeto(Game game) {
		if (!game.getCurrentLeader().isManual()) game.getCurrentLeader().act();
		game.changeTurn();
		if (!game.getCurrentLeader().isManual()) game.getCurrentLeader().act();
		game.changeTurn();
		final User  player1 = game.getCurrent();
		final Space hand1 = game.getCurrentHand();
		final Space deck1 = game.getCurrentDeck();
		final User player2 = game.getOpponent();
		final Space hand2 = game.getOpponentHand();
		final Space deck2 = game.getOpponentDeck();
		handleVeto(player1, hand1, deck1);
		handleVeto(player2, hand2, deck2);
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
		if (opponentPile == myPile) opponentPile = client.getIdentity().getCurrentGame().getCurrentDiscardPile();
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
		if (!isCurrent(client)) return new Result("not your turn", false);
		try {
			Game clientGame = client.getIdentity().getCurrentGame();
			Card card = getHand(client).getCards().get(cardNumber);
			clientGame.placeCard(card, rowNumber);
			clientGame.getMoves().add(new Move(client.getIdentity(), clientGame.getRoundNumber() + "\n" + rowNumber + "\n"
					+ card.toString() + "\nunique code: " + card.toSuperString()));
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
		return new Result(myLeader.isDisable() + "\n" + opponentLeader.isDisable(), true);
	}

	public static Result useLeaderAbility(Client client) {
		if (!isCurrent(client)) return new Result("not your turn", false);
		try {
			Game clientGame = client.getIdentity().getCurrentGame();
			Leader leader = clientGame.getCurrentLeader();
			clientGame.useLeaderAbility();
			clientGame.getMoves().add(new Move(client.getIdentity(), clientGame.getRoundNumber() + "\n" + -1 + "\n"
					+ leader.toString() + "\nunique code: " + leader.toSuperString()));
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result passedState(Client client) {
		boolean hasPassed = client.getIdentity().getCurrentGame().hasPassed();
		boolean hasOpponentPassed = client.getIdentity().getCurrentGame().hasOpponentPassed();
		if (!isCurrent(client)) {
			boolean tmp = hasPassed;
			hasPassed = hasOpponentPassed;
			hasOpponentPassed = tmp;
		}
		return new Result(hasPassed + "\n" + hasOpponentPassed, true);
	}

	public static Result showFactionsForGraphic(Client client) {
		Faction myFaction = client.getIdentity().getCurrentGame().getCurrentFaction();
		Faction opponentFaction = client.getIdentity().getCurrentGame().getOpponentFaction();
		if (!isCurrent(client)) {
			Faction tmp = myFaction;
			myFaction = opponentFaction;
			opponentFaction = tmp;
		}
		return new Result(myFaction + "\n" + opponentFaction, true);
	}


	public static Result showPlayersLives(Client client) {
		int myLife = client.getIdentity().getCurrentGame().getCurrentLife();
		int opponentLife = client.getIdentity().getCurrentGame().getOpponentLife();
		if (!isCurrent(client)) {
			int tmp = myLife;
			myLife = opponentLife;
			opponentLife = tmp;
		}
		return new Result("Current: " + myLife + "\n" + "Opponent: " + opponentLife, true);
	}

	public static Result showHandSize(Client client) {
		Space myHand = getHand(client);
		Space opponentHand = client.getIdentity().getCurrentGame().getOpponentHand();
		if (myHand == opponentHand) opponentHand = client.getIdentity().getCurrentGame().getCurrentHand();
		return new Result(myHand.getCards().size() + " - " + opponentHand.getCards().size(), true);
	}

	public static Result passTurn(Client client) {
		if (!isCurrent(client)) return new Result("not your turn", false);
		client.getIdentity().getCurrentGame().passTurn();
		client.getIdentity().getCurrentGame().getMoves().add(new Move(client.getIdentity(),
				client.getIdentity().getCurrentGame().getRoundNumber() + "\npass"));
		return new Result("Turn passed successfully", true);
	}

	public static Result getOpponentMove(Client client) {
		User opponent = isCurrent(client) ? client.getIdentity().getCurrentGame().getOpponent() :
				client.getIdentity().getCurrentGame().getCurrent();
		return new Result(client.getIdentity().getCurrentGame().getLastMove(opponent), true);
	}

	public static Result isAsking(Client client) {
		if (Asker.isAsking(client.getIdentity())) return new Result("asking", true);
		return new Result("not asking", false);
	}

	public static Result getAskerCards(Client client) {
		if (!Asker.isAsking(client.getIdentity())) return new Result("not asking", false);
		StringBuilder cards = new StringBuilder();
		for (Card card : Asker.getRunning(client.getIdentity()).getCards()) {
			cards.append(card.getName()).append("\n").append(card.getDescription()).append("\n");
		}
		return new Result(cards.toString(), true);
	}

	public static Result getAskerPtr(Client client) {
		if (!Asker.isAsking(client.getIdentity())) return new Result("not asking", false);
		return new Result(String.valueOf(Asker.getRunning(client.getIdentity()).getPtr()), true);
	}

	public static Result isAskerOptional(Client client) {
		if (!Asker.isAsking(client.getIdentity())) return new Result("not asking", false);
		if (Asker.getRunning(client.getIdentity()).isOptional()) return new Result("optional", true);
		return new Result("not optional", false);
	}

	public static Result selectCard(Client client, int index) {
		if (Asker.select(client.getIdentity(), index)) return new Result("success", true);
		return new Result("failure", false);
	}

	public static Result endGame(Client client) {
		client.setMenu(new MatchFinderMenu());
		client.getIdentity().setCurrentGame(null);
		return new Result("game finished", true);
	}

	public static Result isGameOver(Client client) {
		if (client.getIdentity().getCurrentGame().isGameOver())
			return new Result("game is over", true);
		return new Result("game isn't over", false);
	}

	public static Result isGameWin(Client client) {
		if(client.getIdentity().getCurrentGame().isGameWin() == isCurrent(client))
			return new Result("win", true);
		return new Result("lose", false);
	}

	public static Result isGameDraw(Client client) {
		if (client.getIdentity().getCurrentGame().isGameDraw())
			return new Result("draw", true);
		return new Result("not draw", false);
	}

	public static Result getPowers(Client client) {
		int myPower = client.getIdentity().getCurrentGame().getCurrentPower();
		int opponentPower = client.getIdentity().getCurrentGame().getOpponentPower();
		if (!isCurrent(client)) {
			int tmp = myPower;
			myPower = opponentPower;
			opponentPower = tmp;
		}
		return new Result(myPower + "\n" + opponentPower + "\n", true);
	}

	public static Result getScores(Client client) {
		ArrayList<Integer> scores = client.getIdentity().getCurrentGame().getCurrentScores();
		ArrayList<Integer> opponentScores = client.getIdentity().getCurrentGame().getOpponentScores();
		if (!isCurrent(client)) {
			ArrayList<Integer> tmp = scores;
			scores = opponentScores;
			opponentScores = tmp;
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < scores.size(); i++)
			result.append(scores.get(i)).append("\n").append(opponentScores.get(i)).append("\n");
		return new Result(result.toString(), true);
	}

	public static Result cheatClearWeather(Client client) {
		client.getIdentity().getCurrentGame().getCurrentWeatherSystem().clear(client.getIdentity().getCurrentGame().getCurrentDiscardPile(), null);
		client.getIdentity().getCurrentGame().getOpponentWeatherSystem().clear(client.getIdentity().getCurrentGame().getOpponentDiscardPile(), null);
		return new Result("Weather cleared", true);
	}

	public static Result cheatClearRow(Client client, int rowNumber) {
		if (!isCurrent(client)) rowNumber = 5 - rowNumber;
		client.getIdentity().getCurrentGame().getRow(rowNumber).clear(rowNumber < 3 ? client.getIdentity().getCurrentGame().getCurrentDiscardPile() : client.getIdentity().getCurrentGame().getOpponentDiscardPile(), null);
		return new Result("Row cleared", true);
	}

	public static Result cheatDebuffRow(Client client, int rowNumber) {
		Card card = CardCreator.getCard(rowNumber == 2 ? "Biting Frost" : rowNumber == 1 ? "Impenetrable Fog" : "Torrential Rain");
		card.setSpace(client.getIdentity().getCurrentGame().getCurrentDeck());
		try {
			card.put(-1);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
		return new Result("Row debuffed", true);
	}

	public static Result cheatHeal(Client client) {
		if (isCurrent(client)) client.getIdentity().getCurrentGame().setCurrentLife(2);
		else client.getIdentity().getCurrentGame().setOpponentLife(2);
		return new Result("Recovered Crystal", true);
	}

	public static Result cheatAddCard(Client client, String cardName) {
		Card card = CardCreator.getCard(cardName);
		if (card == null) return new Result("Card not found", false);
		card.setSpace(getDeck(client));
		card.updateSpace(getHand(client));
		return new Result("Card added to hand", true);
	}

	public static Result cheatMoveFromDeckToHand(Client client) {
		new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, false, 1, false, false).move(client.getIdentity());
		return new Result("Card moved from deck to hand", true);
	}

	public static Result cheatAddPower(Client client, int power) {
		client.getIdentity().getCurrentGame().addCheatPower(power, isCurrent(client));
		return new Result("Power added", true);
	}

	public static Result getDescription(String cardName) {
		return new Result(Objects.requireNonNull(CardCreator.getCard(cardName)).getDescription(), true);
	}

	public static Result isMyTurn(Client client) {
		if (isCurrent(client)) return new Result("your turn", true);
		return new Result("opponent turn", false);
	}
}
