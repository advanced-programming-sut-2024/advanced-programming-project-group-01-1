package server.model.card.ability;

import server.model.card.Card;
import server.model.game.Game;

public enum Cleanse implements Ability {

	INSTANCE;

	@Override
	public void act(Card card) {
		try {
			Game.getCurrentGame().getCurrentWeatherSystem().clear(Game.getCurrentGame().getCurrentDiscardPile(), null);
			Game.getCurrentGame().getOpponentWeatherSystem().clear(Game.getCurrentGame().getOpponentDiscardPile(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
