package model.card.unit;

import model.card.ability.Ability;

public class Ranged extends Agile {

	public Ranged(String name, Ability ability, String faction, int basePower, boolean isHero) {
		super(name, ability, faction, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		// TODO:
	}

}
