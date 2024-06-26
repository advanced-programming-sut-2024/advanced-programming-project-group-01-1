package model.user;

import model.card.Card;
import model.card.special.Special;

import java.util.ArrayList;

public class Deck {

	private final ArrayList<Card> cards;
	private int specialCount;
	private int unitCount;

	public Deck() {
		this.cards = new ArrayList<>();
	}

	public int getSpecialCount() {
		return this.specialCount;
	}

	public int getUnitCount() {
		return this.unitCount;
	}

	public boolean isValid() {
		return unitCount >= 22 && specialCount <= 10;
	}

	public ArrayList<Card> getCards() {
		return new ArrayList<>(this.cards);
	}

	public boolean add(Card card) {
		return false;
	}

	public boolean remove(Card card) {
		if (cards.remove(card)) {
			if (card instanceof Special) specialCount--;
			else unitCount--;
			return true;
		}
		return false;
	}
}
