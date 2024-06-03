package model.card.special.spell.Buffer;

import model.card.ability.Ability;
import model.card.special.spell.Spell;

public abstract class Buffer extends Spell {

	public Buffer(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

}
