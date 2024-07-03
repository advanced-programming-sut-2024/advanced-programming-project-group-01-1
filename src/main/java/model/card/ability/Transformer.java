package model.card.ability;

import main.CardCreator;
import model.card.Card;
import model.game.Game;
import model.game.space.Row;

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
		transformedCard.setSpace(Game.getCurrentGame().getCurrentDeck());
		boolean isOpponent = Game.getCurrentGame().getRowNumber((Row) card.getSpace()) >= 3;
		try {
			transformedCard.put(isOpponent ? 3 : 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
