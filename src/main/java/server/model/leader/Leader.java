package server.model.leader;

import server.model.Client;

import java.io.Serializable;

public abstract class Leader implements Serializable, Cloneable {
	protected final String name;
	private final String description;
	protected boolean isDisable;
	protected final boolean isManual;

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

	public boolean isManual() {
		return isManual;
	}

	public void act(Client client) {
		this.disable();
	}

	@Override
	public abstract Leader clone();

	@Override
	public String toString() {
		return "Leader: " + name + "\n" + this.getDescription();
	}
}
