package server.view;

import message.Command;
import message.MainMenuCommands;
import server.controller.MainMenuController;
import message.Result;


import static server.controller.MainMenuController.*;

public class MainMenu implements Menuable {

	@Override
	public Result run(Command command) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(command.getCommand()) != null) result = goToMatchFinderMenu();
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(command.getCommand()) != null) result = goToProfileMenu();
		else if (MainMenuCommands.LOGOUT.getMatcher(command.getCommand()) != null) result = MainMenuController.logout();
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null) result = showCurrentMenu();
		else result = new Result("Invalid command", false);
		return result;
	}

}
