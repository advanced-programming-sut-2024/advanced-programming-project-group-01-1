package model.card.special.spell;

import model.card.Card;
import model.card.ability.Debuffer;

public class Weather extends Spell {

	private final boolean[] rowEffects = new boolean[3];

	public Weather(String name, boolean effectsMelee, boolean effectsRanged, boolean effectsSiege) {
		super(name, Debuffer.INSTANCE);
		rowEffects[2] = effectsMelee;
		rowEffects[1] = effectsRanged;
		rowEffects[0] = effectsSiege;
	}

	public boolean[] getRowEffects() {
		return rowEffects;
	}

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

	@Override
	public Card clone() {
		return new Weather(this.name, this.rowEffects[2], this.rowEffects[1], this.rowEffects[0]);
	}
}
