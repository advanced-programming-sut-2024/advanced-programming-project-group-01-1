package model.card.unit;

import model.card.Card;

public abstract class Unit extends Card {

	final int basePower;

	public Unit(String name, int basePower) {
		super(name);
		this.basePower = basePower;
	}

	@Override
	public void put(int row) throws Exception {
		// TODO:
	}

}
