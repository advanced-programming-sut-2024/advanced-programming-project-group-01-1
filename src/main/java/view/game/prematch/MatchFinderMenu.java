package view.game.prematch;

import controller.game.PreMatchMenusController;
import javafx.stage.Stage;
import model.Result;
import view.Menuable;
import view.game.GameMenusCommands;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class MatchFinderMenu implements Menuable {

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
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.CREATE_GAME.getMatcher(input)) != null) {
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
