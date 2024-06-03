package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;
import model.game.space.Space;

public enum MoralBooster implements Ability {
	INSTANCE;

	@Override
	public void act(Card card, Space space) {
		Row row = (Row) space;
		row.setBoostCount(row.getBoostCount() + 1);
		for(Card cardInRow : row.getCards()) {
			if(cardInRow == card) continue;
			Unit unit = (Unit) cardInRow;
			unit.setBoostCount(unit.getBoostCount() + 1);
		}
	}

	@Override
	public void undo(Card card, Space space) {
		Row row = (Row) space;
		row.setBoostCount(row.getBoostCount() - 1);
		for(Card cardInRow : row.getCards()) {
			if(cardInRow == card) continue;
			Unit unit = (Unit) cardInRow;
			unit.setBoostCount(unit.getBoostCount() - 1);
		}
	}
}
