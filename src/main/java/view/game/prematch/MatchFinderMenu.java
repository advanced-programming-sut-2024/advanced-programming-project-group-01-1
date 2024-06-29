package view.game.prematch;

import controller.game.PreMatchMenusController;
import model.Result;
import view.Menuable;
import view.game.MatchMenusCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MatchFinderMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = MatchMenusCommands.CREATE_GAME.getMatcher(input)) != null) {
			result = createGame(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result createGame(Matcher matcher) {
		String opponent = matcher.group("opponent");
		Result result = PreMatchMenusController.createGame(opponent);
		return null;
	}
}
