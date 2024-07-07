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
		ArrayList<Card> cards = new ArrayList<>(this.cards);
		for (Card card : cards) {
			if(card == stayingUnit) continue;
			card.pull();
			card.updateSpace(discardPile);
		}
		cards.clear();
	}

	public ArrayList<Card> getCards(boolean onlyUnit, boolean ignoreHero) {
		ArrayList<Card> cards = new ArrayList<>();
		for (Card card : this.cards) {
			if (card instanceof Unit) {
				if (!((Unit) card).isHero() || !ignoreHero) cards.add(card);
			} else if (!onlyUnit) cards.add(card);
		}
		return cards;
	}

	@Override
	public String toString() {
		StringBuilder space = new StringBuilder();
		cards.forEach(card -> space.append(card.toString()).append("\n------------------\n"));
		return space.toString();
	}
}
