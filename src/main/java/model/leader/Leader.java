package model.leader;

public abstract class Leader {
	String name, faction;

	public Leader(String name, String faction) {
		this.name = name;
		this.faction = faction;
	}

	public abstract void Action() throws Exception;
}
