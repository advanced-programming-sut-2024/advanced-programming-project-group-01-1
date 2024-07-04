package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;


public class LobbyMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if (GameMenusCommands.SHOW_FACTIONS.getMatcher(command) != null)
			result = PreMatchMenusController.showFactions(client);
		else if ((matcher = GameMenusCommands.SELECT_FACTION.getMatcher(command)) != null)
			result = selectFaction(client, matcher);
		else if (GameMenusCommands.SHOW_CARDS.getMatcher(command) != null) result = PreMatchMenusController.showCards(client);
		else if (GameMenusCommands.SHOW_DECK.getMatcher(command) != null) result = PreMatchMenusController.showDeck(client);
		else if (GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getMatcher(command) != null)
			result = PreMatchMenusController.showInfo(client);
		else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_FILE_ADDRESS.getMatcher(command)) != null)
			result = saveDeckWithAddress(client, matcher);
		else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_NAME.getMatcher(command)) != null)
			result = saveDeckWithName(client, matcher);
		else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_FILE_ADDRESS.getMatcher(command)) != null)
			result = loadDeckWithAddress(client, matcher);
		else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_NAME.getMatcher(command)) != null)
			result = loadDeckWithName(client, matcher);
		else if (GameMenusCommands.SHOW_LEADERS.getMatcher(command) != null)
			result = PreMatchMenusController.showLeaders(client);
		else if ((matcher = GameMenusCommands.SELECT_LEADER.getMatcher(command)) != null)
			result = selectLeader(client, matcher);
		else if ((matcher = GameMenusCommands.ADD_TO_DECK.getMatcher(command)) != null) result = addToDeck(client, matcher);
		else if ((matcher = GameMenusCommands.REMOVE_FROM_DECK.getMatcher(command)) != null)
			result = deleteFromDeck(client, matcher);
		else if (GameMenusCommands.PASS_ROUND.getMatcher(command) != null)
			result = PreMatchMenusController.changeTurn(client);
		else if (GameMenusCommands.START_GAME.getMatcher(command) != null)
			result = PreMatchMenusController.startGame(client);
		else result = new Result("Invalid command", false);

		return result;
	}

	private Result loadDeckWithName(Client client, Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.loadDeckByName(client, name);
	}

	private Result loadDeckWithAddress(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(client, address);
	}

	private Result selectFaction(Client client, Matcher matcher) {
		String faction = matcher.group("faction");
		return PreMatchMenusController.selectFaction(client, faction);
	}

	private Result saveDeckWithAddress(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.saveDeckByAddress(client, address);
	}

	private Result saveDeckWithName(Client client, Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.saveDeckByName(client, name);
	}

	private Result loadDeck(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(client, address);
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
