package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.space.Row;

public enum Horn implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		Row row = (Row) card.getSpace();
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			if (unit != card) unit.setHornCount(unit.getHornCount() + 1);
		}
		row.setHornCount(row.getHornCount() + 1);
	}

	@Override
	public void undo(Client client, Card card) {
		Row row = (Row) card.getSpace();
		row.setHornCount(row.getHornCount() - 1);
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			if (unit != card) unit.setHornCount(unit.getHornCount() - 1);
		}
	}
}
