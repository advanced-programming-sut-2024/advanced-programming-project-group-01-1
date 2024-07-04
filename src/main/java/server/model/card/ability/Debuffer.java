package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;
import server.model.card.special.spell.Weather;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Debuffer implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		boolean[] rowEffects = ((Weather) card).getRowEffects();
		for (int i = 0; i < 3; i++) if (rowEffects[i]) {
			debuffRow(client.getIdentity().getCurrentGame().getRow(i));
			debuffRow(client.getIdentity().getCurrentGame().getRow(5 - i));
		}
	}

	@Override
	public void undo(Client client ,Card card) {
		boolean[] rowEffects = ((Weather) card).getRowEffects();
		for (int i = 0; i < 3; i++) if (rowEffects[i]) {
			removeDebuffRow(client.getIdentity().getCurrentGame().getRow(i));
			removeDebuffRow(client.getIdentity().getCurrentGame().getRow(5 - i));
		}
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
