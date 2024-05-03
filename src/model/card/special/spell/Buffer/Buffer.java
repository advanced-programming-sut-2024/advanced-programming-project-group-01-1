package model.card.special.spell.Buffer;

import model.card.special.spell.Spell;

public abstract class Buffer extends Spell {

	public Buffer(String name) {
		super(name);
	}

	@Override
	public void put(int row) throws Exception {
		super.put(row);
	}

}
