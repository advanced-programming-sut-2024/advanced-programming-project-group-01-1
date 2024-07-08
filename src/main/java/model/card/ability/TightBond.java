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

	@Override
	public String getDescription(Card card) {
		return "Multiplies the strength of all units of the same name on the row.";
	}

}
