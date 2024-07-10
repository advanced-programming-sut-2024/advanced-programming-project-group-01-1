package client.controller.game;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.game.ClientTournamentMenu;
import client.view.game.prematch.ClientMatchFinderMenu;
import javafx.application.Platform;
import message.GameMenusCommands;
import message.Result;

public class ClientMatchMenuController {

	public static boolean isRowDebuffed(int rowNumber) {
		String command = GameMenusCommands.IS_ROW_DEBUFFED.getPattern();
		command = command.replace("(?<rowNumber>\\d+)", String.valueOf(rowNumber));
		return TCPClient.send(command).isSuccessful();
	}

	public static Result getUsernames() {
		String command = GameMenusCommands.GET_USERNAMES.getPattern();
		return TCPClient.send(command);
	}

	public static Result showHand(int number) {
		String command = GameMenusCommands.IN_HAND_DECK.getPattern();
		command = command.replace("(?<option>-option)", "");
		command = command.replace("(?<cardNumber>\\d+)", String.valueOf(number));
		return TCPClient.send(command);
	}

	public static Result showHandForGraphic() {
		String command = GameMenusCommands.SHOW_CARDS_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result remainingInDeck() {
		String command = GameMenusCommands.REMAINING_CARDS_TO_PLAY.getPattern();
		return TCPClient.send(command);
	}

	public static Result remainingInDecksForGraphic() {
		String command = GameMenusCommands.SHOW_DECK_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showDiscordPiles() {
		String command = GameMenusCommands.OUT_OF_PLAY_CARDS.getPattern();
		return TCPClient.send(command);
	}

	public static Result showDiscardPilesForGraphic() {
		String command = GameMenusCommands.SHOW_PILE_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showRow(int rowNumber) {
		String command = GameMenusCommands.CARDS_IN_ROW.getPattern();
		command = command.replace("(?<rowNumber>(0|1|2|3|4|5))", String.valueOf(rowNumber));
		return TCPClient.send(command);
	}

	public static Result showRowForGraphic(int rowNumber) {
		String command = GameMenusCommands.SHOW_ROW_GRAPHIC.getPattern();
		command = command.replace("(?<rowNumber>\\d+)", String.valueOf(rowNumber));
		return TCPClient.send(command);
	}

	public static Result showWeatherSystem() {
		String command = GameMenusCommands.SPELLS_IN_PLAY.getPattern();
		return TCPClient.send(command);
	}

	public static Result showWeatherSystemForGraphic() {
		String command = GameMenusCommands.SHOW_WEATHER_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result placeCard(int cardNumber, int rowNumber) {
		String command = GameMenusCommands.PLACE_CARD.getPattern();
		command = command.replace("(?<cardNumber>\\d+)", String.valueOf(cardNumber));
		command = command.replace("(?:(?<inRow> in row )(?<rowNumber>\\d+))", " in row " + rowNumber);
		return TCPClient.send(command);
	}

	public static Result showLeader() {
		String command = GameMenusCommands.SHOW_COMMANDER.getPattern();
		return TCPClient.send(command);
	}

	public static Result showLeadersForGraphic() {
		String command = GameMenusCommands.SHOW_LEADER_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result isLeadersDisable() {
		String command = GameMenusCommands.IS_LEADERS_DISABLE.getPattern();
		return TCPClient.send(command);
	}

	public static Result useLeaderAbility() {
		String command = GameMenusCommands.COMMANDER_POWER_PLAY.getPattern();
		System.out.println("fuck user leader ability");
		Result result = TCPClient.send(command);
		System.out.println(result);
		return result;
	}

	public static Result passedState() {
		String command = GameMenusCommands.PASSED_STATE.getPattern();
		return TCPClient.send(command);
	}

	public static Result showFactionsForGraphic() {
		String command = GameMenusCommands.SHOW_FACTION_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showPlayersInfo() {
		String command = GameMenusCommands.SHOW_PLAYERS_INFO.getPattern();
		return TCPClient.send(command);
	}

	public static Result showPlayersLives() {
		String command = GameMenusCommands.SHOW_PLAYERS_LIVES.getPattern();
		return TCPClient.send(command);
	}

	public static Result showHandSize() {
		String command = GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getPattern();
		return TCPClient.send(command);
	}

	public static Result showCurrentHand() {
		String command = GameMenusCommands.SHOW_CURRENT_HAND.getPattern();
		return TCPClient.send(command);
	}

	public static Result showTurnInfo() {
		String command = GameMenusCommands.SHOW_TURN_INFO.getPattern();
		return TCPClient.send(command);
	}

	public static Result showTotalPower() {
		String command = GameMenusCommands.SHOW_TOTAL_SCORE.getPattern();
		return TCPClient.send(command);
	}

	public static Result showRowPower(int rowNumber) {
		String command = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getPattern();
		command = command.replace("(?<rowNumber>\\d+)", String.valueOf(rowNumber));
		return TCPClient.send(command);
	}

	public static Result passTurn() {
		String command = GameMenusCommands.PASS_ROUND.getPattern();
		return TCPClient.send(command);
	}

	public static Result getOpponentMove() {
		String command = GameMenusCommands.OPPONENT_LAST_MOVE.getPattern();
		return TCPClient.send(command);
	}

	public static Result isAsking() {
		String command = GameMenusCommands.IS_ASKING.getPattern();
		return TCPClient.send(command);
	}

	public static Result getAskerCards() {
		String command = GameMenusCommands.GET_ASKER_CARDS.getPattern();
		return TCPClient.send(command);
	}

	public static Result getAskerPtr() {
		String command = GameMenusCommands.GET_ASKER_PTR.getPattern();
		return TCPClient.send(command);
	}

	public static Result isAskerOptional() {
		String command = GameMenusCommands.IS_ASKER_OPTIONAL.getPattern();
		return TCPClient.send(command);
	}

	public static Result selectCard(int index) {
		String command = GameMenusCommands.SELECT_CARD.getPattern();
		command = command.replace("(?<cardNumber>(-)?\\d+)", String.valueOf(index));
		return TCPClient.send(command);
	}

	public static void endGame() {
		String command = GameMenusCommands.END_GAME.getPattern();
		Result result = TCPClient.send(command);
		if (result.isSuccessful()) {
			ClientAppview.setMenu(new ClientMatchFinderMenu());
		}
	}

	public static boolean isGameOver() {
		String command = GameMenusCommands.IS_GAME_OVER.getPattern();
		return TCPClient.send(command).isSuccessful();
	}

	public static boolean isGameWin() {
		String command = GameMenusCommands.IS_GAME_WIN.getPattern();
		return TCPClient.send(command).isSuccessful();
	}

	public static boolean isGameDraw() {
		String command = GameMenusCommands.IS_GAME_DRAW.getPattern();
		return TCPClient.send(command).isSuccessful();
	}

	public static boolean isMyTurn() {
		String command = GameMenusCommands.IS_MY_TURN.getPattern();
		return TCPClient.send(command).isSuccessful();
	}

	public static Result getPowers() {
		String command = GameMenusCommands.GET_POWERS.getPattern();
		return TCPClient.send(command);
	}

	public static Result getScores() {
		String command = GameMenusCommands.GET_SCORES.getPattern();
		return TCPClient.send(command);
	}

	public static Result getDescription(String cardName) {
		String command = GameMenusCommands.GET_DESCRIPTION.getPattern();
		command = command.replace("(?<cardName>.+)", cardName);
		return TCPClient.send(command);
	}

	public static Result cheatClearWeather() {
		String command = GameMenusCommands.CHEAT_CLEAR_WEATHER.getPattern();
		return TCPClient.send(command);
	}

	public static Result cheatClearRow(int rowNumber) {
		String command = GameMenusCommands.CHEAT_CLEAR_ROW.getPattern();
		command = command.replace("(?<rowNumber>(0|1|2|3|4|5))", String.valueOf(rowNumber));
		return TCPClient.send(command);
	}

	public static Result cheatDebuffRow(int rowNumber) {
		String command = GameMenusCommands.CHEAT_DEBUFF_ROW.getPattern();
		command = command.replace("(?<rowNumber>(0|1|2|3|4|5))", String.valueOf(rowNumber));
        return TCPClient.send(command);
	}

	public static Result cheatHeal() {
		String command = GameMenusCommands.CHEAT_HEAL.getPattern();
		return TCPClient.send(command);
	}

	public static Result cheatAddCard(String cardName) {
		String command = GameMenusCommands.CHEAT_ADD_CARD.getPattern();
		command = command.replace("(?<cardName>.+)", cardName);
        return TCPClient.send(command);
	}

	public static Result cheatMoveFromDeckToHand() {
		String command = GameMenusCommands.CHEAT_MOVE_FROM_DECK.getPattern();
		return TCPClient.send(command);
	}

	public static Result cheatAddPower(int power) {
		String command = GameMenusCommands.CHEAT_ADD_POWER.getPattern();
		command = command.replace("(?<power>\\d+)", String.valueOf(power));
        return TCPClient.send(command);
	}

}
