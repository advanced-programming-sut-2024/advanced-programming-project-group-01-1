package server.controller.enums;

import message.Result;

public enum RegisterMenusResponses {
	DUPLICATE_USERNAME("Username is already taken", false),
	INVALID_USERNAME("Invalid username", false),
	INVALID_EMAIL("Invalid email", false),
	INVALID_PASSWORD("Invalid password", false),
	WEAK_PASSWORD("Weak password", false),
	PASSWORDS_DONT_MATCH("Passwords don't match", false),
	REGISTER_SUCCESSFUL("Register successful, now pick a question for recovery", true),
	INVALID_QUESTION_NUMBER("Invalid question number", false),
	ANSWERS_DONT_MATCH("Answers don't match", false),
	PICK_QUESTION_SUCCESSFUL("Pick question successful", true);

	private final Result result;

	RegisterMenusResponses(String message, boolean success) {
		this.result = new Result(message, success);
	}

	public Result getResult() {
		return result;
	}
}
