package model.card.unit;

import model.card.ability.Ability;

public class Siege extends Unit {

	public Siege(String name, Ability ability, String faction, int basePower, boolean isHero) {
		super(name, ability, faction, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		// TODO:
	}

}
