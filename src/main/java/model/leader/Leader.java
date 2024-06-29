package model.leader;

public abstract class Leader {
	private final String name;
	private boolean isDisable;
	private final boolean isManual;

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
}
