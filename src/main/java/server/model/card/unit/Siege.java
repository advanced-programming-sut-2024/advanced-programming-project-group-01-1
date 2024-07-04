package server.model.card.unit;

import server.model.card.ability.Ability;

public class Siege extends Unit {

	public Siege(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 0 && rowNumber != 5) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

}
