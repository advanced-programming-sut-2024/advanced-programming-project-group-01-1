package server.model.card.special;

import server.model.card.Card;
import server.model.card.ability.Ability;

public abstract class Special extends Card {

	public Special(String name, Ability ability) {
		super(name, ability);
	}

}
