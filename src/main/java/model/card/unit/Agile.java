package model.card.unit;

import model.card.ability.Ability;

public class Agile extends Unit {

	public Agile(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 1 && rowNumber != 2) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

}
