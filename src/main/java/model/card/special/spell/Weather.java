package model.card.special.spell;

import model.card.Card;
import model.card.ability.Debuffer;
import model.game.Game;

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
		if (rowNumber != -1) throw new Exception("Weather can only be put in weather system");
		Game.getCurrentGame().getCurrentWeatherSystem().getCards().add(this);
		this.ability.act(this);
	}

	@Override
	public Card clone() {
		return new Weather(this.name, this.rowEffects[2], this.rowEffects[1], this.rowEffects[0]);
	}
}
