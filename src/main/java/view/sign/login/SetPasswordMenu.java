package view.sign.login;

import controller.sign.LoginMenusController;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class SetPasswordMenu implements Menuable {

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

	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(input)) != null) {
			result = setPassword(matcher);
		} else if ((matcher = LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result showCurrentMenu() {
		return new Result("Set Password Menu", true);
	}

	private Result setPassword(Matcher matcher) {
		String password = matcher.group("password");
		Result result = LoginMenusController.setPassword(password);
		if (result.isSuccessful()) {
			Appview.setMenu(new LoginMenu());
		}
		return result;
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}

}
