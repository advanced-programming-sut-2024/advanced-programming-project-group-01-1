package model.card.ability;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.unit.*;
import model.game.Game;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = MatchMenuController.medicAsk();
		if (unit == null) return;
		Game.getCurrentGame().getCurrentDiscardPile().remove(unit);
		try {
			if (unit instanceof Siege) unit.put(Game.SIEGE_ROW_NUMBER);
			else if (unit instanceof Ranged) unit.put(Game.RANGED_ROW_NUMBER);
			else unit.put(Game.MELEE_ROW_NUMBER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
