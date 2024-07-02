package model.card.ability;

import model.card.Card;
import model.card.unit.*;
import model.game.Game;

import java.util.ArrayList;

public enum Muster implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		String musterName = ((Unit) card).getMusterName();
		ArrayList<Card> aliveCards = new ArrayList<>();
		for (Card cardInHand : Game.getCurrentGame().getCurrentHand().getCards()) aliveCards.add(cardInHand);
		for (Card cardInDeck : Game.getCurrentGame().getCurrentDeck().getCards()) aliveCards.add(cardInDeck);
		for (Card aliveCard : aliveCards) {
			if (!(aliveCard instanceof Unit)) continue;
			Unit unit = (Unit) aliveCard;
			if (unit.getMusterName().startsWith(musterName)) {
				try {
					if (unit instanceof Melee) unit.put(2);
					else if (unit instanceof Ranged) unit.put(1);
					else if (unit instanceof Siege) unit.put(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
