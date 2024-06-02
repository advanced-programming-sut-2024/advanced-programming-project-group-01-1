package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.card.special.Special;

public abstract class Spell extends Special {

	public Spell(String name, Ability ability) {
		super(name, ability);
	}

}
