package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;

public enum Horn implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Row row = (Row) card.getSpace();
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			if (unit != card) unit.setHornCount(unit.getHornCount() + 1);
		}
		row.setHornCount(row.getHornCount() + 1);
	}

	@Override
	public void undo(Card card) {
		Row row = (Row) card.getSpace();
		row.setHornCount(row.getHornCount() - 1);
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			if (unit != card) unit.setHornCount(unit.getHornCount() - 1);
		}
	}
}