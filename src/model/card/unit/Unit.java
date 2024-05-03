package model.card.unit;

import model.card.Card;

public abstract class Unit extends Card {

	final int basePower;
	final String faction;

	public Unit(String name, String faction, int basePower) {
		super(name);
		this.faction = faction;
		this.basePower = basePower;
	}

	@Override
	public void put(int row) throws Exception {
		// TODO:
	}

}
