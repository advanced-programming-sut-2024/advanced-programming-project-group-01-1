package server.model.card.ability;

import server.model.card.Card;
import server.model.card.special.spell.Weather;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Debuffer implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		boolean[] rowEffects = ((Weather) card).getRowEffects();
		for (int i = 0; i < 3; i++) if (rowEffects[i]) {
			debuffRow(card.getGame().getRow(i));
			debuffRow(card.getGame().getRow(5 - i));
		}
	}

	@Override
	public void undo(Card card) {
		boolean[] rowEffects = ((Weather) card).getRowEffects();
		for (int i = 0; i < 3; i++) if (rowEffects[i]) {
			removeDebuffRow(card.getGame().getRow(i));
			removeDebuffRow(card.getGame().getRow(5 - i));
		}
	}

	@Override
	public String getDescription(Card card) {
		return "Debuffs all units on the row.";
	}

	private void debuffRow(Row row) {
		row.setDebuffed(true);
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			unit.setDebuff(true);
		}
	}

	private void removeDebuffRow(Row row) {
		row.setDebuffed(false);
		for (Card cardInRow : row.getCards()) {
			if (!(cardInRow instanceof Unit)) continue;
			Unit unit = (Unit) cardInRow;
			unit.setDebuff(false);
		}
	}

}
