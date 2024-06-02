package model.card.unit;

import model.card.ability.Ability;
import model.card.unit.Agile;

public class Melee extends Agile {

	public Melee(String name, Ability ability, String faction, int basePower) {
		super(name, ability, faction, basePower);
	}

	@Override
	public void put(int row) throws Exception {
		// TODO:
	}

}