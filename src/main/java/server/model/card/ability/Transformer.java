package server.model.card.ability;

import server.main.CardCreator;
import server.model.Client;
import server.model.card.Card;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Transformer implements Ability {
	INSTANCE;

	@Override
	public void undo(Client client, Card card) {
		String transformedName = null;
		if (card.getName().equals("Cow")) transformedName = "Bovine Defence Force";
		else if (card.getName().equals("Kambi")) transformedName = "Hemdall";
		Card transformedCard = CardCreator.getCard(transformedName);
		transformedCard.setSpace(client.getIdentity().getCurrentGame().getCurrentDeck());
		boolean isOpponent = client.getIdentity().getCurrentGame().getRowNumber((Row) card.getSpace()) >= 3;
		try {
			transformedCard.put(client, isOpponent ? 3 : 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
