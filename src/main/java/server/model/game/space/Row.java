package server.model.game.space;

import server.model.card.Card;
import server.model.card.ability.Horn;
import server.model.card.ability.Mardroeme;
import server.model.card.special.Decoy;
import server.model.card.special.spell.Buffer;
import server.model.card.unit.Unit;

public class Row extends Space {

	Buffer buffer;
	int hornCount = 0;
	int boostCount = 0;
	boolean hasMardroeme = false;
	boolean isDebuffed = false;

	public int getSumOfPowers() {
		int sumOfPowers = 0;
		for (Card card : this.cards) {
			if (card instanceof Decoy) continue;
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

	public void clear(Space discardPile, Unit stayingUnit) {
		if (buffer != null) {
			buffer.pull();
			buffer.updateSpace(discardPile);
			buffer = null;
		}
		super.clear(discardPile, stayingUnit);
	}

	@Override
	public String toString() {
		return "Buffer: \n" + (buffer == null ? "None" : buffer.toString()) + "\n" + super.toString();
	}
}
