package server.view.user;

import javafx.fxml.FXML;
import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class ProfileMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.CHANGE_USERNAME.getMatcher(command)) != null)
			result = changeUsername(client, matcher);
		else if ((matcher = UserMenusCommands.CHANGE_PASSWORD.getMatcher(command)) != null)
			result = changePassword(client, matcher);
		else if ((matcher = UserMenusCommands.CHANGE_NICKNAME.getMatcher(command)) != null)
			result = changeNickname(client, matcher);
		else if ((matcher = UserMenusCommands.CHANGE_EMAIL.getMatcher(command)) != null)
			result = changeEmail(client, matcher);
		else if((matcher = UserMenusCommands.SAVE_CHANGES.getMatcher(command)) != null)
			result = saveChanges(client, matcher);
		else if (UserMenusCommands.GET_USERNAME.getMatcher(command) != null)
			result = UserMenusController.getUsername(client);
		else if (UserMenusCommands.GET_NICKNAME.getMatcher(command) != null)
			result = UserMenusController.getNickname(client);
		else if (UserMenusCommands.GET_EMAIL.getMatcher(command) != null)
			result = UserMenusController.getEmail(client);
		else if (UserMenusCommands.ENTER_USER_INFO.getMatcher(command) != null) result = enterUserInfo(client);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command) != null) result = exit(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result saveChanges(Client client, Matcher matcher) {
		String username = matcher.group("username");
		String nickname = matcher.group("nickname");
		String email = matcher.group("email");
		String oldPassword = matcher.group("oldPassword");
		String newPassword = matcher.group("newPassword");
		String confirmNewPassword = matcher.group("confirmNewPassword");
		return UserMenusController.saveChanges(client, username, nickname, email, oldPassword, newPassword, confirmNewPassword);
	}

	private Result changeUsername(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.changeUsername(client, username);
	}

	private Result changePassword(Client client, Matcher matcher) {
		String newPassword = matcher.group("newPassword");
		String oldPassword = matcher.group("oldPassword");
		return UserMenusController.changePassword(client, newPassword, oldPassword);
	}

	private Result changeNickname(Client client, Matcher matcher) {
		String nickname = matcher.group("nickname");
		return UserMenusController.changeNickname(client, nickname);
	}

	private Result changeEmail(Client client, Matcher matcher) {
		String email = matcher.group("email");
		return UserMenusController.changeEmail(client, email);
	}

	@FXML
	private Result enterUserInfo(Client client) {
		return UserMenusController.goToInfoMenu(client);
	}

	private Result showCurrentMenu() {
		return new Result("Profile Menu", true);
	}


	@FXML
	private Result exit(Client client) {
		return UserMenusController.exit(client);
	}

}
