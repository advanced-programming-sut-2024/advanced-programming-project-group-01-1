package server.model.card.special.spell;

import server.model.card.ability.Ability;
import server.model.card.special.Special;

public abstract class Spell extends Special {

	public Spell(String name, Ability ability) {
		super(name, ability);
	}

}
