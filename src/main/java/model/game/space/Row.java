package model.game.space;

import model.card.Card;
import model.card.ability.Horn;
import model.card.ability.Mardroeme;
import model.card.special.spell.Buffer;
import model.card.unit.Unit;

public class Row extends Space {

	Buffer buffer;
	int hornCount = 0;
	int boostCount = 0;
	boolean hasMardroeme = false;
	boolean isDebuffed = false;

	public int getSumOfPowers() {
		int sumOfPowers = 0;
		for (Card card : this.cards) {
			sumOfPowers += ((Unit) card).getPower();
		}
		return sumOfPowers;
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void setBuffer(Buffer buffer) {
		if (this.buffer != null && this.buffer.getAbility() == Horn.INSTANCE) this.hornCount--;
		if (this.buffer != null && this.buffer.getAbility() == Mardroeme.INSTANCE) this.hasMardroeme = false;
		this.buffer = buffer;
		if (this.buffer != null && this.buffer.getAbility() == Horn.INSTANCE) this.hornCount++;
		if (this.buffer != null && this.buffer.getAbility() == Mardroeme.INSTANCE) this.hasMardroeme = true;
	}

	public void setHornCount(int hornCount) {
		this.hornCount = hornCount;
	}

	public int getHornCount() {
		return hornCount;
	}

	public void setBoostCount(int boostCount) {
		this.boostCount = boostCount;
	}

	public int getBoostCount() {
		return boostCount;
	}

	public void setMardroeme(boolean mardroeme) {
		this.hasMardroeme = mardroeme;
	}

	public boolean hasMardroeme() {
		return hasMardroeme;
	}

	public void setDebuffed(boolean debuffed) {
		isDebuffed = debuffed;
	}

	public boolean isDebuffed() {
		return isDebuffed;
	}

	public void clear(Space discardPile, Unit stayingUnit) throws Exception {
		if (buffer != null) {
			buffer.pull();
			buffer = null;
		}
		super.clear(discardPile, stayingUnit);
	}

}
