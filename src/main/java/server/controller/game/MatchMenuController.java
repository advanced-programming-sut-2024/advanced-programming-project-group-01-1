package server.controller.game;

import message.Result;
import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.space.Space;

import java.util.ArrayList;
import java.util.Scanner;

public class MatchMenuController {

	public static Result vetoCard(Client client, int cardNumber) {
		// TODO:
		return null;
	}

	public static Result showHand(Client client, int number) {
		StringBuilder hand = new StringBuilder();
		if (number >= 0) hand.append(client.getIdentity().getCurrentGame().getCurrentHand().getCards().get(number).toString());
		else hand.append(client.getIdentity().getCurrentGame().getCurrentHand().toString());
		return new Result(hand.toString(), true);
	}

	public static Result remainingInDeck(Client client) {
		return new Result(String.valueOf(client.getIdentity().getCurrentGame().getCurrentDeck().getCards().size()), true);
	}

	public static Result showDiscordPiles(Client client) {
		return new Result("Current Discard Pile:\n" + client.getIdentity().getCurrentGame().getCurrentDiscardPile() +
				"Opponent Discard Pile:\n" + client.getIdentity().getCurrentGame().getOpponentDiscardPile(), true);
	}

	public static Result showRow(Client client, int rowNumber) {
		return new Result(client.getIdentity().getCurrentGame().getRow(rowNumber).toString(), true);
	}

	public static Result showWeatherSystem(Client client) {
		return new Result(client.getIdentity().getCurrentGame().getCurrentWeatherSystem() + "\n" +
				client.getIdentity().getCurrentGame().getOpponentWeatherSystem(), true);
	}

	public static Result placeCard(Client client, int cardNumber, int rowNumber) {
		try {
			client.getIdentity().getCurrentGame().placeCard(client, client.getIdentity().getCurrentGame().getCurrentHand().getCards().get(cardNumber), rowNumber);
			return new Result("Card placed successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showLeader(Client client) {
		return new Result(client.getIdentity().getCurrentGame().getCurrentLeader().toString(), true);
	}

	public static Result useLeaderAbility(Client client) {
		try {
			client.getIdentity().getCurrentGame().useLeaderAbility(client);
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}
	}

	public static Result showPlayersInfo(Client client) {
		return new Result("Current: " + client.getIdentity().getCurrentGame().getCurrent().getUsername() + " " +
				client.getIdentity().getCurrentGame().getCurrentFaction() + "\n" +
				"Opponent: " + client.getIdentity().getCurrentGame().getOpponent().getUsername() + " " +
				client.getIdentity().getCurrentGame().getOpponentFaction(), true);
	}

	public static Result showPlayersLives(Client client) {
		return new Result("Current: " + client.getIdentity().getCurrentGame().getCurrentLife() + "\n" +
				"Opponent: " + client.getIdentity().getCurrentGame().getOpponentLife(), true);
	}

	public static Result showHandSize(Client client) {
		return new Result(String.valueOf(client.getIdentity().getCurrentGame().getCurrentHand().getCards().size()), true);
	}

	public static Result showTurnInfo(Client client) {
		return new Result(client.getIdentity().getCurrentGame().getCurrent().getUsername(), true);
	}

	public static Result showTotalPower(Client client) {
		return new Result("Current: " + client.getIdentity().getCurrentGame().getCurrentPower(client) + "\n" +
				"Opponent: " + client.getIdentity().getCurrentGame().getOpponentPower(client), true);
	}

	public static Result showRowPower(Client client, int rowNumber) {
		return new Result(String.valueOf(client.getIdentity().getCurrentGame().getRow(rowNumber).getSumOfPowers(client)), true);
	}

	public static Result passTurn(Client client) {
		client.getIdentity().getCurrentGame().passTurn(client);
		return new Result("Turn passed successfully", true);
	}

	public static Card askSpace(Space space, boolean onlyUnit) {
		//return askSpace(space, false, onlyUnit);
		return null;
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
		// TODO: make it work with graphics and terminal
		Scanner scanner = new Scanner(System.in);
		int index = scanner.nextInt();
		if (index < 0 || index >= availableCards.size()) return null;
		return availableCards.get(index);
	}

	public static void showSpace(Space tmp) {
	}
}
