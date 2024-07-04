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

	public static Result vetoCard(Client client,int cardNumber) {
		// TODO:
		return null;
	}

	public static Result showHand(Client client,int number) {
		/*StringBuilder hand = new StringBuilder();
		if (number >= 0) hand.append(Game.getCurrentGame().getCurrentHand().getCards().get(number).toString());
		else hand.append(Game.getCurrentGame().getCurrentHand().toString());
		return new Result(hand.toString(), true);*/
		return null;
	}

	public static Result remainingInDeck(Client client) {
		//return new Result(String.valueOf(Game.getCurrentGame().getCurrentDeck().getCards().size()), true);
		return null;
	}

	public static Result showDiscordPiles(Client client) {
		/*return new Result("Current Discard Pile:\n" + Game.getCurrentGame().getCurrentDiscardPile() +
				"Opponent Discard Pile:\n" + Game.getCurrentGame().getOpponentDiscardPile(), true);*/
		return null;
	}

	public static Result showRow(Client client,int rowNumber) {
		//return new Result(Game.getCurrentGame().getRow(rowNumber).toString(), true);
		return null;
	}

	public static Result showWeatherSystem(Client client) {
		/*return new Result(Game.getCurrentGame().getCurrentWeatherSystem() + "\n" +
				Game.getCurrentGame().getOpponentWeatherSystem(), true);*/
		return null;
	}

	public static Result placeCard(Client client,int cardNumber, int rowNumber) {
		/*try {
			Game.getCurrentGame().getCurrentHand().getCards().get(cardNumber).put(rowNumber);
			return new Result("Card placed successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}*/
		return null;
	}

	public static Result showLeader(Client client) {
		//return new Result(Game.getCurrentGame().getCurrentLeader().toString(), true);
		return null;
	}

	public static Result useLeaderAbility(Client client) {
		/*try {
			Game.getCurrentGame().useLeaderAbility();
			return new Result("Leader ability played successfully", true);
		} catch (Exception e) {
			return new Result(e.getMessage(), false);
		}*/
		return null;
	}

	public static Result showPlayersInfo(Client client) {
		/*return new Result("Current: " + Game.getCurrentGame().getCurrent().getUsername() + " " +
				Game.getCurrentGame().getCurrentFaction() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponent().getUsername() + " " +
				Game.getCurrentGame().getOpponentFaction(), true);*/
		return null;
	}

	public static Result showPlayersLives(Client client) {
		/*return new Result("Current: " + Game.getCurrentGame().getCurrentLife() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponentLife(), true);*/
		return null;
	}

	public static Result showHandSize(Client client) {
		//return new Result(String.valueOf(Game.getCurrentGame().getCurrentHand().getCards().size()), true);
		return null;
	}

	public static Result showTurnInfo(Client client) {
		//return new Result(Game.getCurrentGame().getCurrent().getUsername(), true);
		return null;
	}

	public static Result showTotalPower(Client client) {
		/*return new Result("Current: " + Game.getCurrentGame().getCurrentPower() + "\n" +
				"Opponent: " + Game.getCurrentGame().getOpponentPower(), true);*/
		return null;
	}

	public static Result showRowPower(Client client,int rowNumber) {
		//return new Result(String.valueOf(Game.getCurrentGame().getRow(rowNumber).getSumOfPowers()), true);
		return null;
	}

	public static Result passTurn(Client client) {
		/*Game.getCurrentGame().passTurn();
		return new Result("Turn passed successfully", true);*/
		return null;
	}

	public static Card askSpace(Space space, boolean onlyUnit) {
		//return askSpace(space, false, onlyUnit);
		return null;
	}

	public static Card askSpace(Space space, boolean isRandom, boolean onlyUnit) {
		/*ArrayList<Card> availableCards = new ArrayList<>();
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
		return availableCards.get(index);*/
		return null;
	}

	public static void showSpace(Client client, Space tmp) {
	}
}
