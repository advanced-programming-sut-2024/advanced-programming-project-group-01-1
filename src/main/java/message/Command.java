package message;

public class Command {
	private final String token;
	private final String command;

	public Command(String token, String command) {
		this.token = token;
		this.command = command;
	}

	public String getToken() {
		return token;
	}

	public String getCommand() {
		return command;
	}
}
