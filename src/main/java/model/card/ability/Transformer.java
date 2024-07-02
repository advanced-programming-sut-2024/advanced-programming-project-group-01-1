package model.card.ability;

import main.CardCreator;
import model.card.Card;

public enum Transformer implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
	}

	@Override
	public void undo(Card card) {
		String transformedName = null;
		if (card.getName().equals("Cow")) transformedName = "Bovine Defence Force";
		else if (card.getName().equals("Kambi")) transformedName = "Hemdall";
		Card transformedCard = CardCreator.getCard(transformedName);
		try {
			transformedCard.put(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
