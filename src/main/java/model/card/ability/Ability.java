package model.card.ability;

import model.card.Card;
import model.game.space.Space;

public interface Ability {
	void act(Card card, Space space) throws Exception;
	void undo(Card card, Space space) throws Exception;
}
