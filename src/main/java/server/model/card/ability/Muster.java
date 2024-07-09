package server.model.card.ability;

import server.model.card.Card;
import server.model.card.unit.*;
import server.model.game.Game;
import server.model.game.space.Row;

import java.util.ArrayList;

public enum Muster implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		String musterName = ((Unit) card).getMusterName();
		ArrayList<Card> aliveCards = new ArrayList<>();
		for (Card cardInHand : card.getGame().getCurrentHand().getCards()) aliveCards.add(cardInHand);
		for (Card cardInDeck : card.getGame().getCurrentDeck().getCards()) aliveCards.add(cardInDeck);
		for (Card aliveCard : aliveCards) {
			if (!(aliveCard instanceof Unit)) continue;
			Unit unit = (Unit) aliveCard;
			if (unit.getSpace() instanceof Row) continue;
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

	@Override
	public String getDescription(Card card) {
		return "Summons all cards with the same name from the deck.";
	}

}
