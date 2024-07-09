package server.model.card.ability;

import server.main.CardCreator;
import server.model.card.Card;
import server.model.card.unit.Melee;
import server.model.card.unit.Ranged;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Berserker implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		if (!((Row) card.getSpace()).hasMardroeme()) return;
		card.pull();
		String transformedName = "Transformed " + card.getName().replace("Berserker", "Vildkaarl");
		Card transformedCard = CardCreator.getCard(transformedName);
		transformedCard.setSpace(card.getGame().getCurrentDeck());
		try {
			if (card instanceof Melee) transformedCard.put(2);
			else if (card instanceof Ranged) transformedCard.put(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription(Card card) {
		return "Transforms into Vildkaarl when Mardroeme is played on the same row.";
	}

}
