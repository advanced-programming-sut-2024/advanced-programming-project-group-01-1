package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class QuickMatchMenu implements Menuable {

	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if (client.isWaiting()) {
			if (GameMenusCommands.IS_WAITING.getMatcher(input) != null) {
				result = new Result("still waiting", true);
			}
			else if (GameMenusCommands.CHECK_MATCH_READY.getMatcher(input) != null) {
				result = PreMatchMenusController.checkMatchReady(client);
			} else if (GameMenusCommands.CANCEL_QUICK_MATCH.getMatcher(input) != null) {
				result = PreMatchMenusController.cancelQuickMatch(client);
			}
			else {
				result = new Result("Invalid command", false);
			}
		} else if (GameMenusCommands.IS_WAITING.getMatcher(input) != null) {
			result = new Result("not waiting", false);
		} else if (GameMenusCommands.QUICK_MATCH_LIST.getMatcher(input) != null) {
			result = PreMatchMenusController.getQuickMatchList();
		} else if ((matcher = GameMenusCommands.START_QUICK_MATCH.getMatcher(input)) != null) {
			result = startQuickMatch(client, matcher);
		} else if (GameMenusCommands.NEW_QUICK_MATCH.getMatcher(input) != null) {
			result = PreMatchMenusController.createNewQuickMatch(client);
		} else if (GameMenusCommands.BACK.getMatcher(input) != null) {
			result = PreMatchMenusController.backToMatchFinder(client);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result startQuickMatch(Client client, Matcher matcher) {
		String opponentUsername = matcher.group("opponent");
		return PreMatchMenusController.startQuickMatch(client, opponentUsername);
	}
}
