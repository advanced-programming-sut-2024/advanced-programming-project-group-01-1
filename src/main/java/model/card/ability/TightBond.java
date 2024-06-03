package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;
import model.game.space.Space;

public enum TightBond implements Ability {
	INSTANCE;

	@Override
	public void act(Card card, Space space) {
		Row row = (Row) space;
		int groupPopulation = 0;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getName().equals(card.getName())) groupPopulation++;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getName().equals(card.getName())) {
				Unit unit = (Unit) cardInRow;
				unit.setMultiplier(groupPopulation);
			}
	}

	@Override
	public void undo(Card card, Space space) {
		Row row = (Row) space;
		int groupPopulation = 0;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getName().equals(card.getName())) groupPopulation++;
		for (Card cardInRow : row.getCards())
			if (cardInRow.getName().equals(card.getName())) {
				Unit unit = (Unit) cardInRow;
				unit.setMultiplier(groupPopulation - 1);
			}
		((Unit) card).setMultiplier(1);
	}
}
