package model.card.special;

import model.card.Card;
import model.card.ability.Ability;

public abstract class Special extends Card {

	public Special(String name, Ability ability) {
		super(name, ability);
	}

}
