package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.card.special.Special;

public abstract class Spell extends Special {

	public Spell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public Card clone() {
		try {
			return this.getClass().getConstructor(String.class, Ability.class).newInstance(this.name, this.ability);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
