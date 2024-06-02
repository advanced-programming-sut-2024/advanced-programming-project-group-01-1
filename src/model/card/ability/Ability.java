package model.card.ability;

import model.card.Card;

public interface Ability {
	void act(Card card);
	void undo(Card card);
}
