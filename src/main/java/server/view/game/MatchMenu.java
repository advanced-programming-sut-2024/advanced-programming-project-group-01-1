package server.view.game;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.MatchMenuController;
import server.model.Client;
import server.view.Menuable;

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
				result = MatchMenuController.cheatClearWeather(client);
			} else if (input.equals("recover crystal")) {
				result = MatchMenuController.cheatHeal(client);
			} else if (input.equals("move from deck to hand")) {
				result = MatchMenuController.cheatMoveFromDeckToHand(client);
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_POWER.getMatcher(input)) != null) {
				int power = Integer.parseInt(matcher.group("power"));
				result = MatchMenuController.cheatAddPower(client, power);
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_CARD.getMatcher(input)) != null) {
				String cardName = matcher.group("cardName");
				result = MatchMenuController.cheatAddCard(client, cardName);
			} else if ((matcher = GameMenusCommands.CHEAT_DEBUFF_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = MatchMenuController.cheatDebuffRow(client, rowNumber);
			} else if ((matcher = GameMenusCommands.CHEAT_CLEAR_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = MatchMenuController.cheatClearRow(client, rowNumber);
			} else if (input.equals("show current menu")) {
				result = new Result("cheat menu", true);
			} else {
				result = new Result("Invalid command", false);
			}
		} else {
			if (MatchMenuController.isAsking(client).isSuccessful()) {
				if (GameMenusCommands.IS_ASKING.getMatcher(input) != null) result = new Result("asking", true);
				else if (GameMenusCommands.GET_ASKER_CARDS.getMatcher(input) != null) {
					result = MatchMenuController.getAskerCards(client);
				} else if (GameMenusCommands.GET_ASKER_PTR.getMatcher(input) != null) {
					result = MatchMenuController.getAskerPtr(client);
				} else if (GameMenusCommands.IS_ASKER_OPTIONAL.getMatcher(input) != null) {
					result = MatchMenuController.isAskerOptional(client);
				} else if ((matcher = GameMenusCommands.SELECT_CARD.getMatcher(input)) != null) {
					result = selectCard(client, matcher);
				} else {
					result = new Result("Invalid command", false);
				}
			} else if (GameMenusCommands.IS_ASKING.getMatcher(input) != null) {
				result = new Result("not asking", false);
			} else if ((matcher = GameMenusCommands.IS_ROW_DEBUFFED.getMatcher(input)) != null) {
				result = isRowDebuffed(client, matcher);
			} else if (GameMenusCommands.GET_USERNAMES.getMatcher(input) != null) {
				result = MatchMenuController.getUsernames(client);
			} else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(input)) != null) {
				result = showHand(client, matcher);
			} else if (GameMenusCommands.SHOW_CARDS_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.showHandForGraphic(client);
			} else if (GameMenusCommands.SHOW_DECK_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.remainingInDecksForGraphic(client);
			} else if (GameMenusCommands.SHOW_PILE_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.showDiscardPilesForGraphic(client);
			} else if ((matcher = GameMenusCommands.SHOW_ROW_GRAPHIC.getMatcher(input)) != null) {
				result = showRowForGraphic(client, matcher);
			} else if (GameMenusCommands.SHOW_WEATHER_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.showWeatherSystemForGraphic(client);
			} else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(input)) != null) {
				result = placeCard(client, matcher);
			} else if (GameMenusCommands.SHOW_LEADER_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.showLeadersForGraphic(client);
			} else if (GameMenusCommands.IS_LEADERS_DISABLE.getMatcher(input) != null) {
				result = MatchMenuController.isLeadersDisable(client);
			} else if (GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(input) != null) {
				result = MatchMenuController.useLeaderAbility(client);
			} else if (GameMenusCommands.PASSED_STATE.getMatcher(input) != null) {
				result = MatchMenuController.passedState(client);
			} else if (GameMenusCommands.SHOW_FACTION_GRAPHIC.getMatcher(input) != null) {
				result = MatchMenuController.showFactionsForGraphic(client);
			} else if (GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(input) != null) {
				result = MatchMenuController.showPlayersLives(client);
			} else if (GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(input) != null) {
				result = MatchMenuController.showHandSize(client);
			} else if (GameMenusCommands.PASS_ROUND.getMatcher(input) != null) {
				result = MatchMenuController.passTurn(client);
			} else if (GameMenusCommands.CHEAT_MENU.getMatcher(input) != null) {
				isCheating = true;
				result = new Result("Cheat menu activated", true);
			} else if (GameMenusCommands.END_GAME.getMatcher(input) != null) {
				result = MatchMenuController.endGame(client);
			} else if (GameMenusCommands.IS_GAME_OVER.getMatcher(input) != null) {
				result = MatchMenuController.isGameOver(client);
			} else if (GameMenusCommands.IS_GAME_WIN.getMatcher(input) != null) {
				result = MatchMenuController.isGameWin(client);
			} else if (GameMenusCommands.IS_GAME_DRAW.getMatcher(input) != null) {
				result = MatchMenuController.isGameDraw(client);
			} else if (GameMenusCommands.GET_POWERS.getMatcher(input) != null) {
				result = MatchMenuController.getPowers(client);
			} else if (GameMenusCommands.GET_SCORES.getMatcher(input) != null) {
				result = MatchMenuController.getScores(client);
			} else {
				result = new Result("Invalid command", false);
			}
		}
		return result;
	}

	private Result showRowForGraphic(Client client, Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRowForGraphic(client, rowNumber);
	}

	private Result isRowDebuffed(Client client, Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.isRowDebuffed(client, rowNumber);
	}

	private Result showHand(Client client, Matcher matcher) {
		boolean option = matcher.group("option") != null;
		if (option) {
			int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
			return MatchMenuController.showHand(client, cardNumber);
		} else {
			return MatchMenuController.showHand(client, -1);
		}
	}

	private Result placeCard(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int rowNumber = matcher.group("rowNumber") != null ? Integer.parseInt(matcher.group("rowNumber")) : -1;
		return MatchMenuController.placeCard(client, cardNumber, rowNumber);
	}

	private Result selectCard(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return MatchMenuController.selectCard(client, cardNumber);
	}
}
