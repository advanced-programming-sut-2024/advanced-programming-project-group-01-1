package model.card.ability;

import model.card.Card;

public interface Ability {
	void act(Card card);
	default void undo(Card card) {}
	String getDescription(Card card);
}
