package view.sign.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenusCommands {
	REGISTER("register -u (?<username>\\w+) -p (?<password>\\w+) (?<passwordConfirm>\\w+) -n (?<nickname>\\w+) -e (?<email>\\w+)"),
	PICK_QUESTION("pick question -q (?<questionNumber>\\d+) -a (?<answer>\\w+) -c (?<answerConfirm>\\w+)"),
	SHOW_CURRENT_MENU("show current menu"),
	EXIT("menu exit");

	private final Pattern pattern;

	RegisterMenusCommands(String regex) {
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
