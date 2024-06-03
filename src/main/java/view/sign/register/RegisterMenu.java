package view.sign.register;

import controller.sign.RegisterMenusController;
import model.Result;
import view.Appview;
import view.Menuable;
import view.sign.login.LoginMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(input)) != null) {
			result = register(matcher);
		} else if ((matcher = RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result register(Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		String passwordConfirm = matcher.group("passwordConfirm");
		String nickname = matcher.group("nickname");
		String email = matcher.group("email");
		Result result = RegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		if (result.isSuccessful()) {
			Appview.setMenu(new PickQuestionMenu());
		}
		return null;
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Matcher matcher) {
		Appview.setMenu(new LoginMenu());
		return null;
	}
}
