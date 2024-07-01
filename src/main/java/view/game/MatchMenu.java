package view.game;

import controller.game.MatchMenuController;
import model.Result;
import view.Menuable;

import java.util.regex.Matcher;


public class MatchMenu implements Menuable {

	@Override
	public void run(String input) {
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
		if (result != null) {
			System.out.println(result);
		}
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
