package model.card;

import model.card.ability.Ability;

public abstract class Card {
	protected final String name;
	protected final Ability ability;

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

}
