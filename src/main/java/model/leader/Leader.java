package model.leader;

import java.io.Serializable;

public abstract class Leader implements Serializable, Cloneable {
	protected final String name;
	protected boolean isDisable;
	protected final boolean isManual;

	public Leader(String name, boolean isManual) {
		this.name = name;
		this.isManual = isManual;
	}

	public String getName() {
		return name;
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

	public abstract void act();

	@Override
	public abstract Leader clone();
}
