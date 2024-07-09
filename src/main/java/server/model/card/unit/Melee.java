package server.model.card.unit;

import server.model.card.ability.Ability;

public class Melee extends Agile {

	public Melee(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 2 && rowNumber != 3) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

	@Override
	public String getDescription() {
		if (ability != null) return super.getDescription();
		return "Can be placed in the melee row.";
	}

}