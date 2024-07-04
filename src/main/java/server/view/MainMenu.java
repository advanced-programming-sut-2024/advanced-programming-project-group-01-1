package server.view;

import message.MainMenuCommands;
import server.controller.MainMenuController;
import message.Result;
import server.model.Client;


import static server.controller.MainMenuController.*;

public class MainMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(command) != null) result = goToMatchFinderMenu(client);
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(command) != null) result = goToProfileMenu(client);
		else if (MainMenuCommands.LOGOUT.getMatcher(command) != null) result = MainMenuController.logout(client);
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = showCurrentMenu(client);
		else result = new Result("Invalid command", false);
		return result;
	}

}
