package model.game.space;

import model.card.Card;

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
}
