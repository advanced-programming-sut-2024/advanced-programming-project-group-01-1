package server.view.sign.login;

import message.LoginMenusCommands;
import message.Result;
import server.controller.sign.LoginMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class AuthenticationMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if (LoginMenusCommands.SEND_EMAIL.getMatcher(command) != null) result = LoginMenusController.handle2FA(client);
		else if ((matcher = LoginMenusCommands.CHECK_CODE.getMatcher(command)) != null) result = checkCode(client, matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = new Result("Authentication Menu", true);
		else if (LoginMenusCommands.EXIT.getMatcher(command) != null) result = LoginMenusController.exit(client);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result checkCode(Client client, Matcher matcher) {
		String code = matcher.group("code");
		return LoginMenusController.checkCode(client, code);
	}
}
