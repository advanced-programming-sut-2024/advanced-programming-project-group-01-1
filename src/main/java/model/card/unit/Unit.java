package model.card.unit;

import model.card.Card;
import model.card.ability.Ability;
import model.game.Game;
import model.game.space.Row;

public abstract class Unit extends Card {

	final int basePower;
	final String faction;
	int hornCount = 0;
	int boostCount = 0;
	int multiplier = 1;
	boolean debuff = false;

	public Unit(String name, Ability ability, String faction, int basePower) {
		super(name, ability);
		this.faction = faction;
		this.basePower = basePower;
	}

	public int getHornCount() {
		return this.hornCount;
	}

	public void setHornCount(int hornCount) {
		this.hornCount = hornCount;
	}

	public int getBoostCount() {
		return this.boostCount;
	}

	public void setBoostCount(int boostCount) {
		this.boostCount = boostCount;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public boolean isDebuff() {
		return this.debuff;
	}

	public void setDebuff(boolean debuff) {
		this.debuff = debuff;
	}

	public int getPower() {
		int power = this.basePower;
		if (this.debuff) power = 1;
		power *= this.multiplier;
		power += this.boostCount;
		if (this.hornCount > 0) power *= 2;
		return power;
	}

	@Override
	public void put(int rowNumber) throws Exception {
	}

}
