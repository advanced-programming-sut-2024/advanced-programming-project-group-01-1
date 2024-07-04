package server.view.user;

import javafx.fxml.FXML;
import message.Command;
import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class ProfileMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.CHANGE_USERNAME.getMatcher(command.getCommand())) != null)
			result = changeUsername(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_PASSWORD.getMatcher(command.getCommand())) != null)
			result = changePassword(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_NICKNAME.getMatcher(command.getCommand())) != null)
			result = changeNickname(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_EMAIL.getMatcher(command.getCommand())) != null)
			result = changeEmail(matcher);
		else if (UserMenusCommands.ENTER_USER_INFO.getMatcher(command.getCommand()) != null) result = enterUserInfo();
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command.getCommand()) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
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

	@FXML
	private Result enterUserInfo() {
		return UserMenusController.goToInfoMenu();
	}

	private Result showCurrentMenu() {
		return new Result("Profile Menu", true);
	}


	@FXML
	private Result exit() {
		return UserMenusController.exit();
	}

}
