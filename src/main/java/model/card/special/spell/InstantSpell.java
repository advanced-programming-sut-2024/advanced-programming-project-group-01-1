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
		this.rowNumber = rowNumber;
		this.setSpace(Game.getCurrentGame().getRow(rowNumber));
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
