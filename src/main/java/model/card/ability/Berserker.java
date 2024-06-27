package model.card.ability;

import main.CardCreator;
import model.card.Card;
import model.card.unit.Melee;
import model.card.unit.Ranged;
import model.game.space.Row;

public enum Berserker implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		try {
			card.pull();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String transformedName = "Transformed " + card.getName().replace("Berserker", "Vildkaarl");
		Card transformedCard = CardCreator.getCard(transformedName);
		try {
			if (card instanceof Melee) transformedCard.put(2);
			else if (card instanceof Ranged) transformedCard.put(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undo(Card card) {

	}
}
