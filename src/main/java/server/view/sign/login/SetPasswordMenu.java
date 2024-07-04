package server.view.sign.login;

import message.Command;
import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class SetPasswordMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(command.getCommand())) != null)
			result = setPassword(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command.getCommand())) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
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
