package model.card.unit;

import model.card.ability.Ability;
import model.card.unit.Unit;

public class Siege extends Unit {

	public Siege(String name, Ability ability, String faction, int basePower) {
		super(name, ability, faction, basePower);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		// TODO:
	}

}
