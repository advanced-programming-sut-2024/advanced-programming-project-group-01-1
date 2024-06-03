package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.space.Row;
import model.game.space.Space;

public enum Horn implements Ability {
	INSTANCE;

	@Override
	public void act(Card card, Space space) {
		Row row = (Row) space;
		for (Card rowCard : row.getCards()) {
			Unit unit = (Unit) rowCard;
			if (unit != card) unit.setHornCount(unit.getHornCount() + 1);
		}
		row.setHornCount(row.getHornCount() + 1);
	}

	@Override
	public void undo(Card card, Space space) {
		Row row = (Row) space;
		row.setHornCount(row.getHornCount() - 1);
		for (Card rowCard : row.getCards()) {
			Unit unit = (Unit) rowCard;
			if (unit != card) unit.setHornCount(unit.getHornCount() - 1);
		}
	}
}
