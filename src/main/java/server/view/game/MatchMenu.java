package server.view.game;

import message.GameMenusCommands;
import server.controller.game.MatchMenuController;

import server.main.CardCreator;
import message.Result;
import server.model.Client;
import server.view.Menuable;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;


public class MatchMenu implements Menuable {


	private boolean isCheating = false;


	@Override
	public Result run(Client client, String input) {
		Result result;
		Matcher matcher;
		if (isCheating) {
			if (input.equals("exit")) {
				isCheating = false;
				result = new Result("Cheat menu deactivated", true);
			} else if (input.equals("clear weather")) {
				result = MatchMenuController.cheatClearWeather();
			} else if (input.equals("recover crystal")) {
				result = MatchMenuController.cheatHeal();
			} else if (input.equals("move from deck to hand")) {
				result = MatchMenuController.cheatMoveFromDeckToHand();
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_POWER.getMatcher(input)) != null) {
				int power = Integer.parseInt(matcher.group("power"));
				result = MatchMenuController.cheatAddPower(power);
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_CARD.getMatcher(input)) != null) {
				String cardName = matcher.group("cardName");
				result = MatchMenuController.cheatAddCard(cardName);
			} else if ((matcher = GameMenusCommands.CHEAT_DEBUFF_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = MatchMenuController.cheatDebuffRow(rowNumber);
			} else if ((matcher = GameMenusCommands.CHEAT_CLEAR_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = MatchMenuController.cheatClearRow(rowNumber);
			} else if (input.equals("show current menu")) {
				result = new Result("cheat menu", true);
			} else {
				result = new Result("Invalid command", false);
			}
		} else {
			if (MatchMenuController.isAsking().isSuccessful()) {
				if ((matcher = GameMenusCommands.SELECT_CARD.getMatcher(input)) != null) {
					result = selectCard(matcher);
				} else {
					result = new Result("Invalid command", false);
				}
			} else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(input)) != null) {
				result = showHand(matcher);
			} else if ((matcher = GameMenusCommands.REMAINING_CARDS_TO_PLAY.getMatcher(input)) != null) {
				result = MatchMenuController.remainingInDeck();
			} else if ((matcher = GameMenusCommands.OUT_OF_PLAY_CARDS.getMatcher(input)) != null) {
				result = MatchMenuController.showDiscordPiles();
			} else if ((matcher = GameMenusCommands.CARDS_IN_ROW.getMatcher(input)) != null) {
				result = showRow(matcher);
			} else if ((matcher = GameMenusCommands.SPELLS_IN_PLAY.getMatcher(input)) != null) {
				result = MatchMenuController.showWeatherSystem();
			} else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(input)) != null) {
				result = placeCard(matcher);
			} else if ((matcher = GameMenusCommands.SHOW_COMMANDER.getMatcher(input)) != null) {
				result = MatchMenuController.showLeader();
			} else if ((matcher = GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(input)) != null) {
				result = MatchMenuController.useLeaderAbility();
			} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(input)) != null) {
				result = MatchMenuController.showPlayersInfo();
			} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(input)) != null) {
				result = MatchMenuController.showPlayersLives();
			} else if ((matcher = GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(input)) != null) {
				result = MatchMenuController.showHandSize();
			} else if ((matcher = GameMenusCommands.SHOW_TURN_INFO.getMatcher(input)) != null) {
				result = MatchMenuController.showTurnInfo();
			} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE.getMatcher(input)) != null) {
				result = MatchMenuController.showTotalPower();
			} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getMatcher(input)) != null) {
				result = showTotalScoreOfRow(matcher);
			} else if ((matcher = GameMenusCommands.PASS_ROUND.getMatcher(input)) != null) {
				result = MatchMenuController.passTurn();
			} else if ((matcher = GameMenusCommands.SHOW_HAND.getMatcher(input)) != null) {
				result = MatchMenuController.showCurrentHand();
			} else if (GameMenusCommands.CHEAT_MENU.getMatcher(input) != null) {
				isCheating = true;
				result = new Result("Cheat menu activated", true);
			} else {
				result = new Result("Invalid command", false);
			}
		}
		return result;
	}

	private Result showHand(Matcher matcher) {
		boolean option = matcher.group("option") != null;
		if (option) {
			int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
			return MatchMenuController.showHand(cardNumber);
		} else {
			return MatchMenuController.showHand(-1);
		}
	}

	private Result showRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRow(rowNumber);
	}

	private Result placeCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int rowNumber = matcher.group("rowNumber") != null ? Integer.parseInt(matcher.group("rowNumber")) : -1;
		return MatchMenuController.placeCard(cardNumber, rowNumber);
	}

	private Result showTotalScoreOfRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRowPower(rowNumber);
	}

	private Result selectCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return MatchMenuController.selectCard(cardNumber);
	}
}
