package model.card.unit;

import model.card.ability.Ability;

public class Siege extends Unit {

	public Siege(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber != 0 && rowNumber != 5) throw new Exception("Invalid row number");
		super.put(rowNumber);
	}

	@Override
	public String getDescription() {
		if (ability != null) return super.getDescription();
		return "Can be placed in the siege row.";
	}

}
