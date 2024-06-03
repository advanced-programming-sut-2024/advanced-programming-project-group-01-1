package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;

public enum TightBond implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Row row = (Row) card.getSpace();
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
	public void undo(Card card) {
		Row row = (Row) card.getSpace();
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
