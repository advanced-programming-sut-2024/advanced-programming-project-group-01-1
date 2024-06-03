package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;

public enum MoralBooster implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Row row = (Row) card.getSpace();
		row.setBoostCount(row.getBoostCount() + 1);
		for(Card cardInRow : row.getCards()) {
			if(cardInRow == card) continue;
			Unit unit = (Unit) cardInRow;
			unit.setBoostCount(unit.getBoostCount() + 1);
		}
	}

	@Override
	public void undo(Card card) {
		Row row = (Row) card.getSpace();
		row.setBoostCount(row.getBoostCount() - 1);
		for(Card cardInRow : row.getCards()) {
			if(cardInRow == card) continue;
			Unit unit = (Unit) cardInRow;
			unit.setBoostCount(unit.getBoostCount() - 1);
		}
	}
}
