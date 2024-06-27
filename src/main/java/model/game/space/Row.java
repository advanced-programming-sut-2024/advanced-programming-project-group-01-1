package model.game.space;

import model.card.special.spell.Buffer;

public class Row extends Space {

	Buffer buffer;
	int hornCount = 0;
	int boostCount = 0;
	boolean hasMardroeme = false;
	boolean isDebuffed = false;
	int sumOfPowers = 0;

	public int getSumOfPowers() {
		return sumOfPowers;
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void setHornCount(int hornCount) {
		this.hornCount = hornCount;
	}

	public int getHornCount() {
		return hornCount;
	}

	public int getBoostCount() {
		return boostCount;
	}

	public void setBoostCount(int boostCount) {
		this.boostCount = boostCount;
	}

	public void setMardroeme(boolean mardroeme) {
		this.hasMardroeme = mardroeme;
	}

	public boolean isDebuffed() {
		return isDebuffed;
	}

	public void setDebuffed(boolean debuffed) {
		isDebuffed = debuffed;
	}

}
