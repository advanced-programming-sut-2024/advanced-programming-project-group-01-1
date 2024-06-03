package model.card;

import model.card.ability.Ability;
import model.game.space.Space;

public abstract class Card {
	protected final String name;
	protected final Ability ability;
	protected Space space;

	public Card(String name, Ability ability) {
		this.name = name;
		this.ability = ability;
	}

	public Ability getAbility() {
		return ability;
	}

	public void put(int rowNumber) throws Exception {

	}

	public void pull() throws Exception {

	}

	public Space getSpace() {
		return space;
	}
}
