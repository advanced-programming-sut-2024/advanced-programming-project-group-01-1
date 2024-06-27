package model.card.special.spell;

import model.card.Card;
import model.card.ability.Ability;
import model.card.ability.Debuffer;

public class Weather extends Spell {

	private final boolean effectsMelee, effectsRanged, effectsSiege;

	public Weather(String name, boolean effectsMelee, boolean effectsRanged, boolean effectsSiege) {
		super(name, Debuffer.INSTANCE);
		this.effectsMelee = effectsMelee;
		this.effectsRanged = effectsRanged;
		this.effectsSiege = effectsSiege;
	}

	public boolean isEffectsMelee() {
		return effectsMelee;
	}

	public boolean isEffectsRanged() {
		return effectsRanged;
	}

	public boolean isEffectsSiege() {
		return effectsSiege;
	}

	@Override
	public void put(int rowNumber) throws Exception {
		super.put(rowNumber);
	}

	@Override
	public Card clone() {
		return new Weather(this.name, this.effectsMelee, this.effectsRanged, this.effectsSiege);
	}
}
