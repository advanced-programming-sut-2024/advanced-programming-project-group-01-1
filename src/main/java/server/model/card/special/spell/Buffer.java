package server.model.card.special.spell;

import server.model.Client;
import server.model.card.Card;
import server.model.card.ability.Ability;
import server.model.game.Game;
import server.model.game.space.Row;
import server.model.game.space.Space;

public class Buffer extends Spell {

	public Buffer(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void updateSpace(Space space) {
		if (!(space instanceof Row)) super.updateSpace(space);
		else {
			this.space.getCards().remove(this);
			this.space = space;
			Row row = (Row) space;
			row.setBuffer(this);
		}
	}

	@Override
	public void put(Client client, int rowNumber) throws Exception {
		if (rowNumber < 0 || rowNumber > 2) throw new Exception("Buffer can only be put in a friendly row");
		Row row = client.getIdentity().getCurrentGame().getRow(rowNumber);
		if (row.getBuffer() != null) throw new Exception("Buffer already exists in this row");
		this.updateSpace(row);
		this.ability.act(client, this);
	}

	@Override
	public Card clone() {
		return new Buffer(this.name, this.ability);
	}
}
