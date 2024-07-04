package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;
import server.model.game.Game;

public enum Cleanse implements Ability {

	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		try {
			client.getIdentity().getCurrentGame().getCurrentWeatherSystem().clear(client, client.getIdentity().getCurrentGame().getCurrentDiscardPile(), null);
			client.getIdentity().getCurrentGame().getOpponentWeatherSystem().clear(client, client.getIdentity().getCurrentGame().getOpponentDiscardPile(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
