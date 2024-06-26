package model.card.special;

import model.card.Card;
import model.card.ability.Ability;

public class Decoy extends Special {

	public Decoy() {
		super("Decoy", null);
	}

	@Override
	public void put(int rowNumber) throws Exception {

	}

	@Override
	public Card clone() {
		return new Decoy();
	}

}
