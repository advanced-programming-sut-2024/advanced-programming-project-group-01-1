package server.model.card.unit;

import server.model.card.ability.Ability;

public class Ranged extends Agile {

	public Ranged(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 1) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

}
