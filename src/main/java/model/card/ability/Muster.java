package model.card.ability;

import model.card.Card;
import model.card.unit.*;
import model.game.Game;
import model.game.space.Row;
import model.game.space.Space;

public enum Muster implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) throws Exception {
		String musterName = ((Unit) card).getMusterName();
		for (Card cardInHand : Game.getCurrentGame().getCurrentHand()) {
			Unit unit = (Unit) cardInHand;
			if (unit.getMusterName().startsWith(musterName)) {
				if (unit instanceof Melee) unit.put(2);
				else if (unit instanceof Ranged) unit.put(1);
				else if (unit instanceof Siege) unit.put(0);
			}
		}
	}

	@Override
	public void undo(Card card) {

	}

}
