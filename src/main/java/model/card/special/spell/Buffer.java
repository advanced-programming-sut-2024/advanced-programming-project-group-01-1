package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.card.ability.Debuffer;
import model.game.Game;
import model.game.space.Row;

public class Buffer extends Spell {

	public Buffer(String name, Ability ability) { super(name, ability); }

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber < 0 || rowNumber > 2) throw new Exception("Buffer can only be put in a friendly row");
		Row row = Game.getCurrentGame().getRow(rowNumber);
		if (row.getBuffer() != null) throw new Exception("Buffer already exists in this row");
		row.setBuffer(this);
		this.setSpace(row);
		this.ability.act(this);
	}

	@Override
	public Card clone() {
		return new Buffer(this.name, this.ability);
	}
}
