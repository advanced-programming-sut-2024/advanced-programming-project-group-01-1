package server.view.sign.register;

import message.Command;
import message.RegisterMenusCommands;
import message.Result;
import server.controller.sign.RegisterMenusController;
import server.view.Menuable;

import java.util.regex.Matcher;

public class RegisterMenu implements Menuable {


	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(command.getCommand())) != null)
			result = register(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null)
			result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(command.getCommand())) != null)
			result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
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
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Matcher matcher) {
		return RegisterMenusController.exit();
	}


}
