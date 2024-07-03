package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.game.Game;

public class InstantSpell extends Spell {

	private int rowNumber;

	public InstantSpell(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != -1) throw new Exception("Instant spells can only be put in the graveyard");
		this.rowNumber = rowNumber;
		this.updateSpace(Game.getCurrentGame().getCurrentDiscardPile());
		ability.act(this);
	}

	public int getRowNumber() {
		return rowNumber;
	}

	@Override
	public Card clone() {
		return new InstantSpell(this.name, this.ability);
	}

}
