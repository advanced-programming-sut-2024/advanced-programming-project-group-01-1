package model.game;

import java.io.Serializable;

public enum Faction implements Serializable {

	NEUTRAL("Neutral", "No special ability."),
	NORTHERN_REALMS("Northern Realms", "Draw a card from your deck whenever you win a round."),
	NILFGAARDIAN_EMPIRE("Nilfgaardian Empire", "Wins any round that ends in a draw."),
	MONSTERS("Monsters", "Keeps a random unit card out after each round."),
	SCOIATAEL("Scoia'tael", "Decides who takes first trun."),
	SKELLIGE("Skellige", "2 random cards from your graveyard are placed back in your deck at the start of the third round.");

	private final String name;
	private final String description;

	Faction(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static Faction getFaction(String factionName) {
		for (Faction value : values()) {
			if (value.name.equals(factionName)) return value;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	@Override
	public String toString() {
		return "faction: " + name + "\n" + description;
	}

}
