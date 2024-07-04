package server.view.sign.login;

import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class LoginMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(command)) != null) result = login(client, matcher);
		else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(command)) != null)
			result = forgotPassword(client, matcher);
		else if ((matcher = LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(command)) != null)
			result = goToRegisterMenu(client, matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command)) != null) result = exit(client, matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Login Menu", true);
	}

	private Result login(Client client, Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
		return LoginMenusController.login(client, username, password, stayLoggedIn);
	}

	private Result forgotPassword(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return LoginMenusController.forgotPassword(client, username);
	}

	private Result goToRegisterMenu(Client client, Matcher matcher) {
		return LoginMenusController.goToRegisterMenu(client);
	}

	private Result exit(Client client, Matcher matcher) {
		return LoginMenusController.exit(client);
	}


}
