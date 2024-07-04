package server.model.card.unit;

import server.model.card.Card;
import server.model.card.ability.Ability;
import server.model.card.ability.Spy;
import server.model.game.Game;
import server.model.game.space.Row;
import server.model.game.space.Space;

public abstract class Unit extends Card {

	final int basePower;
	int hornCount = 0;
	int boostCount = 0;
	int multiplier = 1;
	boolean debuff = false;
	final boolean isHero;

	public Unit(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability);
		this.basePower = basePower;
		this.isHero = isHero;
	}

	public boolean isHero() {
		return isHero;
	}

	public String getMusterName() {
		String displayName = this.getDisplayName();
		int index = displayName.indexOf(":");
		return (index == -1) ? displayName : displayName.substring(0, index + 1);
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

	public int getBasePower() {
		return basePower;
	}

	public int getPower() {
		if (this.isHero) return this.basePower;
		int power = this.basePower;
		if (this.debuff) power = Game.getCurrentGame().isDebuffWeakened() ? power / 2 : 1;
		power *= this.multiplier;
		power += this.boostCount;
		if (this.hornCount > 0) power *= 2;
		if (ability instanceof Spy && Game.getCurrentGame().isSpyPowerDoubled()) power *= 2;
		return power;
	}

	@Override
	public Space getSpace() {
		return super.getSpace();
	}

	@Override
	public void put(int rowNumber) throws Exception {
		Row row = Game.getCurrentGame().getRow(rowNumber);
		this.updateSpace(row);
		this.hornCount = row.getHornCount();
		this.boostCount = row.getBoostCount();
		this.debuff = row.isDebuffed();
		if (this.ability != null) this.ability.act(this);
	}

	@Override
	public void pull() {
		if (space == null) return;
		this.hornCount = 0;
		this.boostCount = 0;
		this.debuff = false;
		super.pull();
	}

	@Override
	public Card clone() {
		try {
			return this.getClass().getConstructor(String.class, Ability.class, int.class, boolean.class).newInstance(this.name, this.ability, this.basePower, this.isHero);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		String unit = super.toString() + "\nPower: " + this.basePower;
		if (this.isHero) unit = "\033[1;33m" + unit + "\033[0m";
		return unit;
	}
}
