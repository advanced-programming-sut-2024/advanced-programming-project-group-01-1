package server.model.card.ability;

import server.model.card.Card;
import server.model.game.Game;

public enum Cleanse implements Ability {

	INSTANCE;

	@Override
	public void act(Card card) {
		try {
			card.getGame().getCurrentWeatherSystem().clear(card.getGame().getCurrentDiscardPile(), null);
			card.getGame().getOpponentWeatherSystem().clear(card.getGame().getOpponentDiscardPile(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription(Card card) {
		return "Clears all weather effects.";
	}

}
