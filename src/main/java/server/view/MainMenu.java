package server.view;

import message.MainMenuCommands;
import message.Result;
import server.controller.MainMenuController;
import server.model.Client;

import java.util.regex.Matcher;

import static server.controller.MainMenuController.*;

public class MainMenu implements Menuable {

	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(command) != null) result = goToMatchFinderMenu(client);
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(command) != null) result = goToProfileMenu(client);
		else if (MainMenuCommands.LOGOUT.getMatcher(command) != null) result = MainMenuController.logout(client);
		else if (MainMenuCommands.GET_LOGGED_IN_USERNAME.getMatcher(command) != null)
			result = MainMenuController.getLoggedInUsername(client);
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(command) != null) result = showCurrentMenu();
		else if (MainMenuCommands.SHOW_FRIENDS.getMatcher(command) != null) result = showFriends(client);
		else if (MainMenuCommands.SHOW_RECEIVED_FRIEND_REQUESTS.getMatcher(command) != null)
			result = showReceivedFriendRequests(client);
		else if (MainMenuCommands.SHOW_SENT_FRIEND_REQUESTS.getMatcher(command) != null)
			result = showSentFriendRequests(client);
		else if ((matcher = MainMenuCommands.ACCEPT_FRIEND_REQUEST.getMatcher(command)) != null)
			result = acceptFriendRequest(client, matcher);
		else if ((matcher = MainMenuCommands.DECLINE_FRIEND_REQUEST.getMatcher(command)) != null)
			result = declineFriendRequest(client, matcher);
		else if ((matcher = MainMenuCommands.REMOVE_FRIEND.getMatcher(command)) != null)
			result = removeFriend(client, matcher);
		else if ((matcher = MainMenuCommands.SEND_FRIEND_REQUEST.getMatcher(command)) != null)
			result = sendFriendRequest(client, matcher);
		else if ((matcher = MainMenuCommands.UNSEND_FRIEND_REQUEST.getMatcher(command)) != null)
			result = unsendFriendRequest(client, matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result acceptFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return MainMenuController.acceptFriendRequest(client, username);
	}

	private Result declineFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return MainMenuController.declineFriendRequest(client, username);
	}

	private Result removeFriend(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return MainMenuController.removeFriend(client, username);
	}

	private Result sendFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return MainMenuController.sendFriendRequest(client, username);
	}

	private Result unsendFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return MainMenuController.unsendFriendRequest(client, username);
	}

}
