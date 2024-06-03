package model;

public class Result {

	private final String message;
	private final boolean isSuccessful;

	public Result(String message, boolean isSuccessful) {
		this.message = message;
		this.isSuccessful = isSuccessful;
	}

	public String getMessage() {
		return this.message;
	}

	public boolean isSuccessful() {
		return this.isSuccessful;
	}

	@Override
	public String toString() {
		if (this.message == null || this.message.length() == 0) return "";
		return this.message.substring(0, this.message.length() - (this.message.charAt(this.message.length() - 1) == '\n' ? 1 : 0));
	}
}
