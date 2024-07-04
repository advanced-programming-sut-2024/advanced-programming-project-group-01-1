package server.view.sign.login;

import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class SetPasswordMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(command)) != null)
			result = setPassword(client, matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if (LoginMenusCommands.EXIT.getMatcher(command) != null) result = exit(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Set Password Menu", true);
	}

	private Result setPassword(Client client, Matcher matcher) {
		String password = matcher.group("password");
		return LoginMenusController.setPassword(client, password);
	}

	private Result exit(Client client) {
		return LoginMenusController.exit(client);
	}

}
