package model.card.unit;

import model.card.ability.Ability;
import model.card.unit.Agile;

public class Melee extends Agile {

	public Melee(String name, Ability ability, String faction, int basePower, boolean isHero) {
		super(name, ability, faction, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		// TODO:
	}

}