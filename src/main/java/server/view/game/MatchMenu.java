package server.view.game;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.MatchMenuController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class MatchMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.VETO_CARD.getMatcher(command)) != null) result = vetoCard(client, matcher);
		else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(command)) != null)
			result = showHand(client, matcher);
		else if (GameMenusCommands.REMAINING_CARDS_TO_PLAY.getMatcher(command) != null)
			result = MatchMenuController.remainingInDeck(client);
		else if (GameMenusCommands.OUT_OF_PLAY_CARDS.getMatcher(command) != null)
			result = MatchMenuController.showDiscordPiles(client);
		else if ((matcher = GameMenusCommands.CARDS_IN_ROW.getMatcher(command)) != null)
			result = showRow(client, matcher);
		else if (GameMenusCommands.SPELLS_IN_PLAY.getMatcher(command) != null)
			result = MatchMenuController.showWeatherSystem(client);
		else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(command)) != null)
			result = placeCard(client, matcher);
		else if (GameMenusCommands.SHOW_COMMANDER.getMatcher(command) != null)
			result = MatchMenuController.showLeader(client);
		else if (GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(command) != null)
			result = MatchMenuController.useLeaderAbility(client);
		else if (GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(command) != null)
			result = MatchMenuController.showPlayersInfo(client);
		else if (GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(command) != null)
			result = MatchMenuController.showPlayersLives(client);
		else if (GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(command) != null)
			result = MatchMenuController.showHandSize(client);
		else if (GameMenusCommands.SHOW_TURN_INFO.getMatcher(command) != null)
			result = MatchMenuController.showTurnInfo(client);
		else if (GameMenusCommands.SHOW_TOTAL_SCORE.getMatcher(command) != null)
			result = MatchMenuController.showTotalPower(client);
		else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getMatcher(command)) != null)
			result = showTotalScoreOfRow(client, matcher);
		else if (GameMenusCommands.PASS_ROUND.getMatcher(command) != null)
			result = MatchMenuController.passTurn(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result vetoCard(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return MatchMenuController.vetoCard(client, cardNumber);
	}

	private Result showHand(Client client, Matcher matcher) {
		boolean option = matcher.group("option") != null;
		if (option) {
			int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
			return MatchMenuController.showHand(client, cardNumber);
		} else {
			return MatchMenuController.showHand(client,-1);
		}
	}

	private Result showRow(Client client, Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRow(client, rowNumber);
	}

	private Result placeCard(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int rowNumber = matcher.group("rowNumber") != null ? Integer.parseInt(matcher.group("rowNumber")) : -1;
		return MatchMenuController.placeCard(client, cardNumber, rowNumber);
	}

	private Result showTotalScoreOfRow(Client client, Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRowPower(client, rowNumber);
	}
}
