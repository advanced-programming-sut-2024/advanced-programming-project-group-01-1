package model.game.space;

import model.card.Card;
import model.card.unit.Unit;

import java.util.ArrayList;

public class Space {

	ArrayList<Card> cards;

	public Space() {
		cards = new ArrayList<>();
	}

	public Space(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void clear(Space discardPile, Unit stayingUnit) {
		for (Card card : cards) {
			if(card == stayingUnit) continue;
			card.pull();
			discardPile.getCards().add(card);
		}
		cards.clear();
	}

	@Override
	public String toString() {
		StringBuilder space = new StringBuilder();
		cards.forEach(card -> space.append(card.toString()).append("\n------------------\n"));
		return space.toString();
	}
}
