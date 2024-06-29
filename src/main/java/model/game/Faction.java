package model.game;

public enum Faction {

	NEUTRAL("Neutral"),
	NORTHERN_REALMS("Northern Realms"),
	NILFGAARDIAN_EMPIRE("Nilfgaardian Empire"),
	MONSTERS("Monsters"),
	SCOIATAEL("Scoia'tael"),
	SKELLIGE("Skellige");
	private final String name;

	Faction(String name) {
		this.name = name;
	}

	public static Faction getFaction(String factionName) {
		for (Faction value : values()) {
			if (value.name.equals(factionName)) return value;
		}
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

}
