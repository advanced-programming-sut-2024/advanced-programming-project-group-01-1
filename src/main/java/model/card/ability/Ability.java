package model.card.ability;

import model.card.Card;

public interface Ability {
	void act(Card card) throws Exception;
	void undo(Card card) throws Exception;
}
