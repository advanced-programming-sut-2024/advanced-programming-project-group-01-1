package server.controller.enums;

import java.util.regex.Pattern;

public enum Validation {

	USERNAME("[a-zA-Z\\d-]+"),
	CORRECT_PASSWORD("[a-zA-Z\\d!@#$%^&*]+"),
//	STRONG_PASSWORD("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}"),
	STRONG_PASSWORD("^.*$"),
//	EMAIL(".+@.+\\..+");
	EMAIL("^.*$");
	// TODO: use the commented patterns

	private final Pattern pattern;

	Validation(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	public boolean matches(String string) {
		return this.pattern.matcher(string).matches();
	}

}
