package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
	ENTER_GAME_MENU("menu enter game menu"),
	ENTER_PROFILE_MENU("menu enter profile menu"),
	ENTER_SOCIAL_MENU("menu enter social menu"),
	ENTER_LEADERBOARD_MENU("menu enter leaderboard menu"),
	GET_LOGGED_IN_USERNAME("get logged in username"),
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
