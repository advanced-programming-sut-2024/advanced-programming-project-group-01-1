package controller.enums;

import java.util.regex.Pattern;

public enum Validation {

	USERNAME("[a-zA-Z\\d-]+"),
	CORRECT_PASSWORD("[a-zA-Z\\d!@#$%^&*]+"),
	//STRONG_PASSWORD("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}"),
	//EMAIL(".+@.+\\..+");
	STRONG_PASSWORD(".+"),
	EMAIL(".+");
	private final Pattern pattern;

	Validation(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	public boolean matches(String string) {
		return this.pattern.matcher(string).matches();
	}

}
