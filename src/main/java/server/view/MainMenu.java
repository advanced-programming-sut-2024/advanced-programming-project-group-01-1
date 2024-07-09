package server.view;

import message.MainMenuCommands;
import message.Result;
import server.controller.MainMenuController;
import server.model.Client;

import java.util.regex.Matcher;

import static client.controller.ClientMainMenuController.goToSocialMenu;
import static server.controller.MainMenuController.*;

public class MainMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(command) != null) result = goToMatchFinderMenu(client);
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(command) != null) result = goToProfileMenu(client);
		else if (MainMenuCommands.ENTER_SOCIAL_MENU.getMatcher(command) != null) result = MainMenuController.goToSocialMenu(client);
		else if (MainMenuCommands.ENTER_LEADERBOARD_MENU.getMatcher(command) != null) result = MainMenuController.goToRankingMenu(client);
		else if (MainMenuCommands.LOGOUT.getMatcher(command) != null) result = MainMenuController.logout(client);
		else if (MainMenuCommands.GET_LOGGED_IN_USERNAME.getMatcher(command) != null)
			result = MainMenuController.getLoggedInUsername(client);
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = showCurrentMenu();
		else result = new Result("Invalid command", false);
		return result;
	}

}
