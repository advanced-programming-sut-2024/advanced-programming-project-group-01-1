package model.card;

import model.card.ability.Ability;

public abstract class Card {
	final String name;
	final Ability ability;

	public Card(String name, Ability ability) {
		this.name = name;
		this.ability = ability;
	}

	public Ability getAbility() {
		return ability;
	}

	public void put(int row) throws Exception {

	}

	public void pull() throws Exception {

	}

}
