package view.sign.login;

import controller.sign.LoginMenusController;
import model.Result;
import view.Appview;
import view.Menuable;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SetPasswordMenu implements Menuable {

	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenuCommands.SET_PASSWORD.getMatcher(input)) != null) {
			result = setPassword(matcher);
		} else if ((matcher = LoginMenuCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else{
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
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
		Appview.setMenu(new LoginMenu());
		return null;
	}

}
