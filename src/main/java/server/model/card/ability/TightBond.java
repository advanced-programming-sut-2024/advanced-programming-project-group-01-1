package server.model.card.ability;

import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.space.Row;

public enum TightBond implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Row row = (Row) card.getSpace();
		int groupPopulation = 0;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getDisplayName().equals(card.getDisplayName())) groupPopulation++;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getDisplayName().equals(card.getDisplayName())) {
				Unit unit = (Unit) cardInRow;
				unit.setMultiplier(groupPopulation);
			}
	}

	@Override
	public void undo(Card card) {
		Row row = (Row) card.getSpace();
		int groupPopulation = 0;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getDisplayName().equals(card.getDisplayName())) groupPopulation++;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getDisplayName().equals(card.getDisplayName())) {
				Unit unit = (Unit) cardInRow;
				unit.setMultiplier(groupPopulation - 1);
			}
		((Unit) card).setMultiplier(1);
	}
}
