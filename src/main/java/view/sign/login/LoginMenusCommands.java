package view.sign.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenusCommands {
	LOGIN("login -u (?<username>\\w+) -p (?<password>\\w+)(?<stayLoggedIn> -stay-logged-in)?"),
	ENTER_REGISTER_MENU("menu enter register menu"),
	FORGOT_PASSWORD("forgot-password -u (?<username>\\w+)"),
	ANSWER_QUESTION("answer -q (?<questionNumber>\\d+) -a (?<answer>\\w+)"),
	SET_PASSWORD("set-password -p (?<password>\\w+)"),
	SHOW_CURRENT_MENU("show current menu"),
	EXIT("menu exit");

	private final Pattern pattern;

	LoginMenusCommands(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public Matcher getMatcher(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return matcher;
		}
		return null;
	}
}
