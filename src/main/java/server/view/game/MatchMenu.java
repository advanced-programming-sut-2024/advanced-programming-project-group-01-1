package server.view.game;

import message.Command;
import message.GameMenusCommands;
import server.controller.game.MatchMenuController;
import javafx.stage.Stage;
import message.Result;
import server.view.Menuable;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class MatchMenu implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	@Override
	public void start(Stage stage) {
		// TODO:
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.VETO_CARD.getMatcher(command.getCommand())) != null) {
			result = vetoCard(matcher);
		} else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(command.getCommand())) != null) {
			result = showHand(matcher);
		} else if (GameMenusCommands.REMAINING_CARDS_TO_PLAY.getMatcher(command.getCommand()) != null) {
			result = MatchMenuController.remainingInDeck();
		} else if (GameMenusCommands.OUT_OF_PLAY_CARDS.getMatcher(command.getCommand()) != null) {
			result = MatchMenuController.showDiscordPiles();
		} else if ((matcher = GameMenusCommands.CARDS_IN_ROW.getMatcher(command.getCommand())) != null) {
			result = showRow(matcher);
		} else if ((matcher = GameMenusCommands.SPELLS_IN_PLAY.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showWeatherSystem();
		} else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(command.getCommand())) != null) {
			result = placeCard(matcher);
		} else if ((matcher = GameMenusCommands.SHOW_COMMANDER.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showLeader();
		} else if ((matcher = GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.useLeaderAbility();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showPlayersInfo();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showPlayersLives();
		} else if ((matcher = GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showHandSize();
		} else if ((matcher = GameMenusCommands.SHOW_TURN_INFO.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showTurnInfo();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.showTotalPower();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getMatcher(command.getCommand())) != null) {
			result = showTotalScoreOfRow(matcher);
		} else if ((matcher = GameMenusCommands.PASS_ROUND.getMatcher(command.getCommand())) != null) {
			result = MatchMenuController.passTurn();
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result vetoCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return MatchMenuController.vetoCard(cardNumber);
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
}
