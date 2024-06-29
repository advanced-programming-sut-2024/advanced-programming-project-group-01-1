package view.sign.login;

import controller.sign.LoginMenusController;
import model.Result;
import view.Appview;
import view.Menuable;
import view.sign.register.RegisterMenu;

import java.util.regex.Matcher;

public class LoginMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(input)) != null) {
			result = login(matcher);
		} else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(input)) != null) {
			result = forgotPassword(matcher);
		} else if ((matcher = LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(input)) != null) {
			result = goToRegisterMenu(matcher);
		} else if ((matcher = LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		}
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
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
