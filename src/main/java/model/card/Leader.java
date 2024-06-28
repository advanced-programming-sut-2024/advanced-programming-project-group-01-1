package model.card;

import model.card.ability.Ability;

public class Leader extends Card {

	public Leader(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public Card clone() {
		return new Leader(name, ability);
	}
}
