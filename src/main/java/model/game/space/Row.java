package model.game.space;

import model.card.Card;
import model.card.special.spell.Buffer.Buffer;

public class Row extends Space {

	Buffer buffer;
	int hornCount = 0;
	boolean hasMardroeme = false;
	boolean isDebuffed = false;
	int sumOfPowers = 0;

	@Override
	public void add(Card card) throws Exception {

	}

	@Override
	public void remove(Card card) throws Exception {

	}

	public int getSumOfPowers() {
		return sumOfPowers;
	}

	public void setBuffer(Buffer buffer) throws Exception {
		this.buffer = buffer;
		return;
	}

	public void removeBuff() throws Exception {
		// TODO:
		return;
	}

	public void debuff() throws Exception {
		// TODO:
		return;
	}

	public void cleanse() throws Exception {
		// TODO:
		return;
	}

	public void setHornCount(int hornCount) {
		this.hornCount = hornCount;
	}

	public int getHornCount() {
		return hornCount;
	}

	public void setMardroeme(boolean mardroeme) {
		this.hasMardroeme = mardroeme;
	}
}
