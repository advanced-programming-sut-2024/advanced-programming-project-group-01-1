package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;


public class LobbyMenu implements Menuable {

	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if (GameMenusCommands.SHOW_FACTIONS.getMatcher(input) != null)
			result = PreMatchMenusController.showFactions(client);
		else if ((matcher = GameMenusCommands.SELECT_FACTION.getMatcher(input)) != null)
			result = selectFaction(client, matcher);
		else if (GameMenusCommands.SHOW_CARDS.getMatcher(input) != null)
			result = PreMatchMenusController.showCards(client);
		else if (GameMenusCommands.SHOW_DECK.getMatcher(input) != null)
			result = PreMatchMenusController.showDeck(client);
		else if (GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getMatcher(input) != null)
			result = PreMatchMenusController.showInfo(client);
		else if (GameMenusCommands.SAVE_DECK.getMatcher(input) != null)
			result = PreMatchMenusController.saveDeck(client);
		else if ((matcher = GameMenusCommands.LOAD_DECK.getMatcher(input)) != null)
			result = loadDeck(client, matcher);
		else if (GameMenusCommands.SHOW_LEADERS.getMatcher(input) != null)
			result = PreMatchMenusController.showLeaders(client);
		else if ((matcher = GameMenusCommands.SELECT_LEADER.getMatcher(input)) != null)
			result = selectLeader(client, matcher);
		else if ((matcher = GameMenusCommands.ADD_TO_DECK.getMatcher(input)) != null)
			result = addToDeck(client, matcher);
		else if ((matcher = GameMenusCommands.REMOVE_FROM_DECK.getMatcher(input)) != null)
			result = deleteFromDeck(client, matcher);
		else if (GameMenusCommands.PASS_ROUND.getMatcher(input) != null)
			result = PreMatchMenusController.changeTurn(client);
		else if (GameMenusCommands.SHOW_CARDS_GRAPHIC.getMatcher(input) != null)
			result = PreMatchMenusController.showCardsForGraphic(client);
		else if (GameMenusCommands.SHOW_DECK_GRAPHIC.getMatcher(input) != null)
			result = PreMatchMenusController.showDeckForGraphic(client);
		else if (GameMenusCommands.SHOW_FACTION_GRAPHIC.getMatcher(input) != null)
			result = PreMatchMenusController.showNowFactionToGraphics(client);
		else if (GameMenusCommands.SHOW_LEADER_GRAPHIC.getMatcher(input) != null)
			result = PreMatchMenusController.showNowLeaderToGraphics(client);
		else if (GameMenusCommands.START_GAME.getMatcher(input) != null)
			result = PreMatchMenusController.startGame(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result loadDeck(Client client, Matcher matcher) {
		String fson = matcher.group("deckFson");
		return PreMatchMenusController.loadDeck(client, fson);
	}

	private Result selectFaction(Client client, Matcher matcher) {
		String faction = matcher.group("faction");
		return PreMatchMenusController.selectFaction(client, faction);
	}

	private Result selectLeader(Client client, Matcher matcher) {
		int leaderNumber = Integer.parseInt(matcher.group("leaderNumber"));
		return PreMatchMenusController.selectLeader(client, leaderNumber);
	}

	private Result addToDeck(Client client, Matcher matcher) {
		String cardName = matcher.group("cardName");
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.addToDeck(client, cardName, count);
	}

	private Result deleteFromDeck(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.deleteFromDeck(client, cardNumber, count);
	}

}
