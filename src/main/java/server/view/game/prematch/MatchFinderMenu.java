package server.view.game.prematch;

import message.Command;
import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class MatchFinderMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.CREATE_GAME.getMatcher(command.getCommand())) != null) {
			result = createGame(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result createGame(Matcher matcher) {
		String opponent = matcher.group("opponent");
		Result result = PreMatchMenusController.createGame(opponent);
		return null;
	}
}
