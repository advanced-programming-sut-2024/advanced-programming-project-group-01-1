package server.view.game.prematch;

import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;


public class MatchFinderMenu implements Menuable {

	boolean isWaiting = false;

	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if (isWaiting) {
			if (GameMenusCommands.STOP_WAIT.getMatcher(input) != null)
				result = PreMatchMenusController.stopWait(client);
			else if (GameMenusCommands.CHECK_REQUEST.getMatcher(input) != null)
				result = PreMatchMenusController.checkRequest(client);
			else if (GameMenusCommands.IS_WAITING.getMatcher(input) != null) result = new Result("still waiting", true);
			else result = new Result("Invalid command", false);
			return result;
		}
		if ((matcher = GameMenusCommands.SEND_MATCH_REQUEST.getMatcher(input)) != null)
			result = sendMatchRequest(client, matcher);
		else if (GameMenusCommands.GET_MATCH_REQUESTS.getMatcher(input) != null)
			result = PreMatchMenusController.getMatchRequests(client);
		else if ((matcher = GameMenusCommands.HANDLE_MATCH_REQUEST.getMatcher(input)) != null)
			result = handleMatchRequest(client, matcher);
		else if (GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(input) != null)
			result = PreMatchMenusController.getOtherUsernames(client);
		else if (GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(input) != null)
			result = PreMatchMenusController.exit(client);
		else if (GameMenusCommands.IS_WAITING.getMatcher(input) != null) result = new Result("not waiting", false);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result sendMatchRequest(Client client, Matcher matcher) {
		String opponentUsername = matcher.group("opponent");
		return PreMatchMenusController.requestMatch(client, opponentUsername);
	}

	private Result handleMatchRequest(Client client, Matcher matcher) {
		boolean accept = matcher.group("handle").equals("accept");
		String senderUsername = matcher.group("sender");
		return PreMatchMenusController.handleMatchRequest(client, senderUsername, accept);
	}


	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean waiting) {
		isWaiting = waiting;
	}

}
