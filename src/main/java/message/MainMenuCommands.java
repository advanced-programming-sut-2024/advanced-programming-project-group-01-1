package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
	ENTER_GAME_MENU("menu enter game menu"),
	ENTER_PROFILE_MENU("menu enter profile menu"),
	GET_LOGGED_IN_USERNAME("get logged in username"),
	SHOW_FRIENDS("show friends"),
	SHOW_RECEIVED_FRIEND_REQUESTS("show received friend requests"),
	SHOW_SENT_FRIEND_REQUESTS("show sent friend requests"),
	ACCEPT_FRIEND_REQUEST("accept friend request (?<username>.+)"),
	DECLINE_FRIEND_REQUEST("decline friend request (?<username>.+)"),
	REMOVE_FRIEND("remove friend (?<username>.+)"),
	SEND_FRIEND_REQUEST("send friend request (?<username>.+)"),
	UNSEND_FRIEND_REQUEST("unsend friend request (?<username>.+)"),
	SHOW_CURRENT_MENU("show current menu"),
	LOGOUT("user logout");

	private final Pattern pattern;

	MainMenuCommands(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public Matcher getMatcher(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return matcher;
		}
		return null;
	}

	public String getPattern() {
		return pattern.pattern();
	}
}
