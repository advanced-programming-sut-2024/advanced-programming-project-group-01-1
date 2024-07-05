package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenusCommands {
	LOGIN("login -u (?<username>\\S+) -p (?<password>\\S+)(?<stayLoggedIn> -stay-logged-in)?"),
	ENTER_REGISTER_MENU("menu enter register menu"),
	FORGOT_PASSWORD("forgot-password -u (?<username>\\S+)"),
	GET_QUESTION("get question"),
	ANSWER_QUESTION("answer -a (?<answer>\\S+)"),
	SET_PASSWORD("set-password -p (?<password>\\S+)"),
	SEND_EMAIL("send email"),
	CHECK_CODE("check code -c (?<code>\\d+)"),
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

	public String getPattern() {
		return pattern.pattern();
	}
}
