package client.view.game;

import client.controller.game.MatchMenuController;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;
import client.view.Menuable;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class MatchMenu implements Menuable {


	@Override
	public void createStage(){
		launch();
	}

	@Override
	public void start(Stage stage) {
		// TODO:
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.VETO_CARD.getMatcher(input)) != null) {
			result = vetoCard(matcher);
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
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result vetoCard(Matcher matcher) {
		return null;
	}

	private Result showHand(Matcher matcher) {
		return null;
	}

	private Result showRow(Matcher matcher) {
		return null;
	}

	private Result placeCard(Matcher matcher) {
		return null;
	}

	private Result showTotalScoreOfRow(Matcher matcher) {
		return null;
	}
}
