package server.model.game.space;

import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;

import java.util.ArrayList;

public class Space {

	ArrayList<Card> cards;
	transient Game game;

	public Space() {
		cards = new ArrayList<>();
	}

	public Space(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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
