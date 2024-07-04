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
			debuffRow(Game.getCurrentGame().getRow(i));
			debuffRow(Game.getCurrentGame().getRow(5 - i));
		}
	}

	@Override
	public void undo(Card card) {
		boolean[] rowEffects = ((Weather) card).getRowEffects();
		for (int i = 0; i < 3; i++) if (rowEffects[i]) {
			removeDebuffRow(Game.getCurrentGame().getRow(i));
			removeDebuffRow(Game.getCurrentGame().getRow(5 - i));
		}
	}

	private void debuffRow(Row row) {
		row.setDebuffed(true);
		for (Card rowCard : row.getCards()) {
			if (!(rowCard instanceof Unit)) continue;
			Unit unit = (Unit) rowCard;
			unit.setDebuff(true);
		}
	}

	private void removeDebuffRow(Row row) {
		row.setDebuffed(false);
		for (Card rowCard : row.getCards()) {
			if (!(rowCard instanceof Unit)) continue;
			Unit unit = (Unit) rowCard;
			unit.setDebuff(false);
		}
	}

}