package server.model.game;

import server.model.user.User;

public class Move {
	private final User mover;
	private final String description;

	public Move(User mover, String description) {
		this.mover = mover;
		this.description = description;
	}

	public User getMover() {
		return mover;
	}

	public String getDescription() {
		return description;
	}
}
