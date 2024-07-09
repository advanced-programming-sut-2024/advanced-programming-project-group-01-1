package server.model.card.ability;

import server.main.CardCreator;
import server.model.card.Card;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Transformer implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
	}

	@Override
	public void undo(Card card) {
		String transformedName = null;
		if (card.getName().equals("Cow")) transformedName = "Bovine Defense Force";
		else if (card.getName().equals("Kambi")) transformedName = "Hemdall";
		Card transformedCard = CardCreator.getCard(transformedName);
		transformedCard.setSpace(card.getGame().getCurrentDeck());
		boolean isOpponent = card.getGame().getRowNumber((Row) card.getSpace()) >= 3;
		try {
			transformedCard.put(isOpponent ? 3 : 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription(Card card) {
		return "Transforms into a different card when destroyed.";
	}

}
