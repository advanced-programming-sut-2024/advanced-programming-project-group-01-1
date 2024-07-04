package server.model.card.ability;

import server.main.CardCreator;
import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.Melee;
import server.model.card.unit.Ranged;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Berserker implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		if (!((Row) card.getSpace()).hasMardroeme()) return;
		card.pull(client);
		String transformedName = "Transformed " + card.getName().replace("Berserker", "Vildkaarl");
		System.out.println(transformedName);
		Card transformedCard = CardCreator.getCard(transformedName);
		transformedCard.setSpace(client.getIdentity().getCurrentGame().getCurrentDeck());
		try {
			if (card instanceof Melee) transformedCard.put(client, 2);
			else if (card instanceof Ranged) transformedCard.put(client, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
