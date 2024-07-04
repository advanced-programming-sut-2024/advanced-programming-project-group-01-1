package server.model.card.special.spell;

import server.model.Client;
import server.model.card.Card;
import server.model.card.ability.Ability;
import server.model.game.Game;

public class InstantSpell extends Spell {

	private int rowNumber;

	public InstantSpell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(Client client, int rowNumber) throws Exception {
		if (rowNumber != -1) throw new Exception("Instant spells can only be put in the graveyard");
		this.rowNumber = rowNumber;
		this.updateSpace(client.getIdentity().getCurrentGame().getCurrentDiscardPile());
		ability.act(client, this);
	}

	public int getRowNumber() {
		return rowNumber;
	}

	@Override
	public Card clone() {
		return new InstantSpell(this.name, this.ability);
	}

}
