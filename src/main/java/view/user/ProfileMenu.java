package view.user;

import controller.UserMenusController;
import model.Result;
import view.Menuable;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements Menuable {

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.CHANGE_USERNAME.getMatcher(input)) != null) {
			result = changeUsername(matcher);
		} else if ((matcher = UserMenusCommands.CHANGE_PASSWORD.getMatcher(input)) != null) {
			result = changePassword(matcher);
		} else if ((matcher = UserMenusCommands.CHANGE_NICKNAME.getMatcher(input)) != null) {
			result = changeNickname(matcher);
		} else if ((matcher = UserMenusCommands.CHANGE_EMAIL.getMatcher(input)) != null) {
			result = changeEmail(matcher);
		} else if ((matcher = UserMenusCommands.ENTER_USER_INFO.getMatcher(input)) != null) {
			result = enterUserInfo();
		} else if ((matcher = UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = UserMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit();
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result changeUsername(Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.changeUsername(username);
	}

	private Result changePassword(Matcher matcher) {
		String newPassword = matcher.group("newPassword");
		String oldPassword = matcher.group("oldPassword");
		return UserMenusController.changePassword(newPassword, oldPassword);
	}

	private Result changeNickname(Matcher matcher) {
		String nickname = matcher.group("nickname");
		return UserMenusController.changeNickname(nickname);
	}

	private Result changeEmail(Matcher matcher) {
		String email = matcher.group("email");
		return UserMenusController.changeEmail(email);
	}

	private Result enterUserInfo() {
		return UserMenusController.goToInfoMenu();
	}

	private Result showCurrentMenu() {
		return new Result("Profile Menu", true);
	}

	private Result exit() {
		return UserMenusController.exit();
	}

}
