package model.card.unit;

import model.card.Card;
import model.card.ability.Ability;

public abstract class Unit extends Card {

	final int basePower;
	final String faction;
	int hornCount = 0;
	int boostCount = 0;
	int multiplier = 1;
	boolean debuff = false;

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

	public Unit(String name, Ability ability, String faction, int basePower) {
		super(name, ability);
		this.faction = faction;
		this.basePower = basePower;
	}

	@Override
	public void put(int row) throws Exception {
		// TODO:
	}

}
