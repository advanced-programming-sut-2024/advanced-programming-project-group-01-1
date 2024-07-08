package model.card.unit;

import model.card.ability.Ability;

public class Ranged extends Agile {

	public Ranged(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 1 && rowNumber != 4) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

	@Override
	public String getDescription() {
		if (ability != null) return super.getDescription();
		return "Can be placed in the ranged row.";
	}

}
