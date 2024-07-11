package server.model.card.special.spell;

import server.model.card.Card;
import server.model.card.ability.Ability;
import server.model.game.Game;
import server.model.game.Move;

public class InstantSpell extends Spell {

	private int rowNumber;

	public InstantSpell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != -1) throw new Exception("Instant spells can only be put in the graveyard");
		this.rowNumber = rowNumber;
		game.getMoves().add(new Move(game.getCurrent(), rowNumber + "\n" + this +
				"\nunique code: " + this.toSuperString()));
		ability.act(this);
		this.updateSpace(this.game.getCurrentDiscardPile());
	}

	public int getRowNumber() {
		return rowNumber;
	}

	@Override
	public Card clone() {
		return new InstantSpell(this.name, this.ability);
	}

}
