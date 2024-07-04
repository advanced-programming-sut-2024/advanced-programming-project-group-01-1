package server.view.sign.register;

import message.RegisterMenusCommands;
import message.Result;
import server.controller.sign.RegisterMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class RegisterMenu implements Menuable {


	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(command)) != null)
			result = register(client, matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(command)) != null)
			result = exit(client, matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result register(Client client, Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		String passwordConfirm = matcher.group("passwordConfirm");
		String nickname = matcher.group("nickname");
		String email = matcher.group("email");
		return RegisterMenusController.register(client, username, password, passwordConfirm, nickname, email);
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Client client, Matcher matcher) {
		return RegisterMenusController.exit(client);
	}


}
