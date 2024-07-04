package server.view.sign.login;

import message.Command;
import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class LoginMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(command.getCommand())) != null) result = login(matcher);
		else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(command.getCommand())) != null)
			result = forgotPassword(matcher);
		else if ((matcher = LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(command.getCommand())) != null)
			result = goToRegisterMenu(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command.getCommand())) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Login Menu", true);
	}

	private Result login(Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
		return LoginMenusController.login(username, password, stayLoggedIn);
	}

	private Result forgotPassword(Matcher matcher) {
		String username = matcher.group("username");
		return LoginMenusController.forgotPassword(username);
	}

	private Result goToRegisterMenu(Matcher matcher) {
		return LoginMenusController.goToRegisterMenu();
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}


}
