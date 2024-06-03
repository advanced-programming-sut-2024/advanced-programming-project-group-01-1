package view.sign.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
	LOGIN("login -u (?<username>\\w+) -p (?<password>\\w+)(?<stayLoggedIn> -stay-logged-in)?"),
	ENTER_REGISTER_MENU("menu enter register"),
	FORGOT_PASSWORD("forgot-password -u (?<username>\\w+)"),
	ANSWER_QUESTION("answer -q (?<questionNumber>\\d+) -a (?<answer>\\w+)"),
	SET_PASSWORD("set-password -p (?<password>\\w+)"),
	EXIT("menu exit");

	private final Pattern pattern;

	LoginMenuCommands(String regex) {
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
