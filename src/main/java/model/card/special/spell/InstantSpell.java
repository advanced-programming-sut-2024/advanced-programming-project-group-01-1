package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;

public class InstantSpell extends Spell {

	public InstantSpell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != -1) throw new Exception("Instant spell can only be put in the graveyard");
		ability.act(this);
	}

	@Override
	public Card clone() {
		return new InstantSpell(this.name, this.ability);
	}

}
