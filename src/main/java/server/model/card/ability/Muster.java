package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.*;
import server.model.game.Game;

import java.util.ArrayList;

public enum Muster implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		String musterName = ((Unit) card).getMusterName();
		ArrayList<Card> aliveCards = new ArrayList<>();
		for (Card cardInHand : client.getIdentity().getCurrentGame().getCurrentHand().getCards()) aliveCards.add(cardInHand);
		for (Card cardInDeck : client.getIdentity().getCurrentGame().getCurrentDeck().getCards()) aliveCards.add(cardInDeck);
		for (Card aliveCard : aliveCards) {
			if (!(aliveCard instanceof Unit)) continue;
			Unit unit = (Unit) aliveCard;
			if (unit.getMusterName().startsWith(musterName)) {
				try {
					if (unit instanceof Melee) unit.put(client, 2);
					else if (unit instanceof Ranged) unit.put(client, 1);
					else if (unit instanceof Siege) unit.put(client, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
