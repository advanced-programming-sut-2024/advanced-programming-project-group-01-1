package server.view.user;

import message.UserMenusCommands;
import message.Result;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import server.model.Client;
import server.view.Menuable;

import java.util.regex.Matcher;


public class SocialMenu implements Menuable {


	@Override
	public Result run(Client client, String command) {
		Matcher matcher;
		Result result = null;
		if (UserMenusCommands.SHOW_FRIENDS.getMatcher(command) != null) result = UserMenusController.showFriends(client);
		else if (UserMenusCommands.SHOW_RECEIVED_FRIEND_REQUESTS.getMatcher(command) != null)
			result = UserMenusController.showReceivedFriendRequests(client);
		else if (UserMenusCommands.SHOW_SENT_FRIEND_REQUESTS.getMatcher(command) != null)
			result = UserMenusController.showSentFriendRequests(client);
		else if ((matcher = UserMenusCommands.ACCEPT_FRIEND_REQUEST.getMatcher(command)) != null)
			result = acceptFriendRequest(client, matcher);
		else if ((matcher = UserMenusCommands.DECLINE_FRIEND_REQUEST.getMatcher(command)) != null)
			result = declineFriendRequest(client, matcher);
		else if ((matcher = UserMenusCommands.REMOVE_FRIEND.getMatcher(command)) != null)
			result = removeFriend(client, matcher);
		else if ((matcher = UserMenusCommands.SEND_FRIEND_REQUEST.getMatcher(command)) != null)
			result = sendFriendRequest(client, matcher);
		else if ((matcher = UserMenusCommands.UNSEND_FRIEND_REQUEST.getMatcher(command)) != null)
			result = unsendFriendRequest(client, matcher);
		else if (UserMenusCommands.SHOW_PLAYERS_INFO.getMatcher(command) != null)
			result = UserMenusController.showPlayersInfo(client);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command) != null)
			result = new Result("Social Menu", true);
		else if (UserMenusCommands.EXIT.getMatcher(command) != null)
			result = UserMenusController.exit(client);
		else return new Result("Invalid command", false);
		return result;
	}

	private Result acceptFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.acceptFriendRequest(client, username);
	}

	private Result declineFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.declineFriendRequest(client, username);
	}

	private Result removeFriend(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.removeFriend(client, username);
	}

	private Result sendFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.sendFriendRequest(client, username);
	}

	private Result unsendFriendRequest(Client client, Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.unsendFriendRequest(client, username);
	}
	
}
