package server.model.card.unit;

import server.model.card.ability.Ability;

public class Melee extends Agile {

	public Melee(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 2) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

}