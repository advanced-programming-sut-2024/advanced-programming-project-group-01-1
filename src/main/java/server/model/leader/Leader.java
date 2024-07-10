package server.model.leader;

import server.model.game.Game;

import java.io.Serializable;

public abstract class Leader implements Serializable, Cloneable {
	protected final String name;
	private final String description;
	protected boolean isDisable;
	protected final boolean isManual;
	protected transient Game game;

	public Leader(String name, String description, boolean isManual) {
		this.name = name;
		this.description = description;
		this.isManual = isManual;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDisable() {
		return isDisable;
	}

	public void disable() {
		isDisable = true;
	}

	public void enable() {
		isDisable = false;
	}

	public boolean isManual() {
		return isManual;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void act() {
		this.disable();
	}

	@Override
	public abstract Leader clone();

	public String toSuperString() {
		return super.toString();
	}

	@Override
	public String toString() {
		return "Leader: " + name + "\n" + this.getDescription();
	}
}