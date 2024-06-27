package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.card.ability.Debuffer;

public class Buffer extends Spell {

	public Buffer(String name, Ability ability) { super(name, ability); }

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

	@Override
	public Card clone() {
		return new Buffer(this.name, this.ability);
	}
}
