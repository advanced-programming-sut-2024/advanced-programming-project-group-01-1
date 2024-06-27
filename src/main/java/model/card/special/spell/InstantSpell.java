package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;

public class InstantSpell extends Spell {

	public InstantSpell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		ability.act(this);
	}

	@Override
	public Card clone() {
		return new InstantSpell(this.name, this.ability);
	}

}
