package server.model.card.ability;

import server.controller.game.MatchMenuController;
import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.*;
import server.model.game.Game;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		Unit unit = (Unit) MatchMenuController.askSpace(client.getIdentity().getCurrentGame().getCurrentDiscardPile(),
				client.getIdentity().getCurrentGame().isMedicRandom(), true);
		if (unit == null) return;
		client.getIdentity().getCurrentGame().getCurrentDiscardPile().getCards().remove(unit);
		client.getIdentity().getCurrentGame().putRevived(client, unit, false);
	}
}
