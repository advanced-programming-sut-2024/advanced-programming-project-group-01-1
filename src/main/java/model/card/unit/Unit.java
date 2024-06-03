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

	public String getMusterName(){
		int index = this.name.indexOf(":");
		return (index == -1) ? this.name : this.name.substring(0, index + 1);
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
		Row row = Game.getCurrentGame().getRow(rowNumber);
		this.space = row;
		row.getCards().add(this);
		this.hornCount = row.getHornCount();
		this.boostCount = row.getBoostCount();
		this.debuff = row.isDebuffed();
		if (this.ability != null) this.ability.act(this);
	}

	@Override
	public void pull() throws Exception {
		if (this.ability != null) this.ability.undo(this);
		this.hornCount = 0;
		this.boostCount = 0;
		this.debuff = false;
		this.getSpace().getCards().remove(this);
		this.space = null;
	}

	@Override
	public Row getSpace() {
		return (Row) super.getSpace();
	}
}
