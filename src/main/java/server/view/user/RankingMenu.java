package server.view.user;

import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;

public class RankingMenu implements Menuable {
	
	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.SHOW_PAGE_INFO.getMatcher(command)) != null) {
			result = UserMenusController.getPage(client, Integer.parseInt(matcher.group("pageNumber")));
		} else if (UserMenusCommands.GET_PAGE_COUNT.getMatcher(command) != null) {
			result = UserMenusController.getPageCount(client);
		} else if ((matcher = UserMenusCommands.CHECK_ONLINE.getMatcher(command)) != null) {
			result = UserMenusController.getOnlineStatus(matcher.group("username"));
		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) {
			result = new Result("Ranking Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(command) != null) {
			result = UserMenusController.exit(client);
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}
	
}
