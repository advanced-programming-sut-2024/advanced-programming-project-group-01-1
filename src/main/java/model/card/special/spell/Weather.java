package model.card.special.spell;

import model.card.ability.Ability;
import model.card.ability.Debuffer;

public class Weather extends Spell {

	private boolean effectsMelee, effectsRanged, effectsSiege;

	public Weather(String name, boolean effectsMelee, boolean effectsRanged, boolean effectsSiege) {
		super(name, Debuffer.INSTANCE);
		this.effectsMelee = effectsMelee;
		this.effectsRanged = effectsRanged;
		this.effectsSiege = effectsSiege;
	}

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

}
