package model.card.special.spell.weather;

import model.card.ability.Ability;
import model.card.special.spell.Spell;

public abstract class Weather extends Spell {

	public Weather(String name, Ability ability) {
		super(name, ability);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

}
