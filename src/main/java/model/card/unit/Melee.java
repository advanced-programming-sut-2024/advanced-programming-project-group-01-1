package model.card.unit;

import model.card.ability.Ability;
import model.card.unit.Agile;

public class Melee extends Agile {

	public Melee(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 2 && rowNumber != 3) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

}